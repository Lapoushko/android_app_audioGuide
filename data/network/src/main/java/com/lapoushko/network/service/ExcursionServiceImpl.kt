package com.lapoushko.network.service

import android.content.Context
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.lapoushko.domain.entity.Excursion
import com.lapoushko.domain.service.ExcursionService
import com.lapoushko.domain.service.TypeFile
import com.lapoushko.network.entity.ExcursionNetwork
import com.lapoushko.network.entity.Point
import com.lapoushko.network.mapper.ExcursionNetworkMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

/**
 * @author Lapoushko
 */
class ExcursionServiceImpl(
    private val context: Context,
    private val mapper: ExcursionNetworkMapper,
    private val downloadService: DownloadService
) : ExcursionService {
    private val fireStore: FirebaseFirestore = Firebase.firestore

    private fun getExcursions(typeSearch: TypeSearch? = null): Flow<List<Excursion>> =
        callbackFlow {
            var query: Query = fireStore.collection("excursions_v.01")

            when (typeSearch) {
                TypeSearch.Popular -> query = query.whereGreaterThan("rating", 0)
                    .orderBy("rating", Query.Direction.DESCENDING)

                is TypeSearch.Category -> query =
                    query.whereArrayContains("categories", typeSearch.category)

                TypeSearch.New -> query = query.whereEqualTo("countRating", 0)

                is TypeSearch.Recommendation -> query =
                    query.whereArrayContainsAny("categories", typeSearch.excursion.categories)

                null -> {}
            }

            query.get()
                .addOnSuccessListener { querySnapshot ->
                    val excursions = mutableListOf<Excursion>()
                    for (document in querySnapshot) {
                        val excursion =
                            document.toObject(ExcursionNetwork::class.java).copy(id = document.id)
                        if (typeSearch is TypeSearch.Recommendation) {
                            if (typeSearch.excursion.id == document.id) {
                                continue
                            }
                        }

                        document.reference.collection("points").orderBy("id").get()
                            .addOnSuccessListener { pointsSnapshot ->
                                val points =
                                    pointsSnapshot.map { it.toObject(Point::class.java) }
                                excursions.add(mapper.toDomain(excursion.copy(points = points)))
                                trySend(excursions)
                            }
                            .addOnFailureListener { e ->
                                println("Error retrieving points: ${e.message}")
                                trySend(emptyList())
                            }
                    }
                }
                .addOnFailureListener { e ->
                    println("Error retrieving excursions: ${e.message}")
                    trySend(emptyList())
                }
            awaitClose {}
        }

    override fun getInterestingExcursions(): Flow<List<Excursion>> {
        return getExcursions()
    }

    override fun getPopularityExcursions(): Flow<List<Excursion>> {
        return getExcursions(TypeSearch.Popular)
    }

    override fun getExcursionsByCategory(category: String): Flow<List<Excursion>> {
        return getExcursions(TypeSearch.Category(category))
    }

    override fun getNewExcursions(): Flow<List<Excursion>> {
        return getExcursions(TypeSearch.New)
    }

    override fun getRecommendation(excursion: Excursion): Flow<List<Excursion>> {
        return getExcursions(TypeSearch.Recommendation(excursion))
    }

    override suspend fun getSize(excursion: Excursion): Double {
        var totalSize = 0.0
        excursion.points.forEach { point ->
            totalSize += getFileSize(point.image)
            totalSize += getFileSize(point.audio)
        }
        return totalSize
    }

    private suspend fun getFileSize(url: String): Double {
        return withContext(Dispatchers.IO) {
            val response = downloadService.getHeadFile(url)
            if (response.isSuccessful) {
                val contentLength = response.headers()["Content-Length"]?.toLongOrNull()
                contentLength?.let {
                    contentLength.toMByte()
                } ?: 0.0
            } else 0.0
        }
    }

    override suspend fun saveExcursion(
        excursion: Excursion,
        callBackFileDownloaded: (Double) -> Unit
    ): Excursion? {
        val points = mutableListOf<com.lapoushko.domain.entity.Point>()
        excursion.points.forEachIndexed { index, point ->
            val image = downloadFile(url = point.image, typeFile = TypeFile.IMAGE) ?: return null
            callBackFileDownloaded(image.second)
            val audio = downloadFile(url = point.audio, typeFile = TypeFile.AUDIO) ?: return null
            callBackFileDownloaded(audio.second)
            points += excursion.points[index].copy(audio = audio.first, image = image.first)
        }
        return excursion.copy(points = points)
    }

    private suspend fun downloadFile(url: String, typeFile: TypeFile): Pair<String, Double>? {
        return withContext(Dispatchers.IO) {
            val response = downloadService.downloadFile(url)
            val randomId = UUID.randomUUID().toString()
            val contentLength = response.body()?.contentLength() ?: -1L
            if (response.isSuccessful) {
                val inputStream = response.body()?.byteStream()
                val text = typeFile.naming
                val file = File(context.filesDir, "excursion_${text}_$randomId")
                try {
                    inputStream?.use { input ->
                        FileOutputStream(file).use { output ->
                            val buffer = ByteArray(4096)
                            var bytesRead: Int
                            while (input.read(buffer).also { bytesRead = it } != -1) {
                                output.write(buffer, 0, bytesRead)
                            }
                        }
                    }
                    Log.d("Download", "Файл скачан в ${file.absolutePath}")
                    Pair(file.absolutePath, contentLength.toMByte())
                } catch (e: Exception) {
                    Log.e("Download", "Ошибка при сохранении файла", e)
                    null
                }
            } else {
                Log.e("Download", "Ошибка загрузки: ${response.code()}")
                null
            }
        }
    }

//        return withContext(Dispatchers.IO){
//            val response = downloadService.downloadFile(url)
//            val randomId = UUID.randomUUID().toString()
//            if (response.isSuccessful) {
//                // Получаем поток данных
//                val inputStream = response.body()?.byteStream()
//
//                val text = typeFile.naming
//                // Создаём файл в internal storage
//                val file = File(context.filesDir, "excursion_${text}_$randomId")
//
//                try {
//                    // Сохраняем данные в файл
//                    inputStream?.use { input ->
//                        FileOutputStream(file).use { output ->
//                            val buffer = ByteArray(4096)
//                            var bytesRead: Int
//                            while (input.read(buffer).also { bytesRead = it } != -1) {
//                                output.write(buffer, 0, bytesRead)
//                            }
//                        }
//                    }
//                    // Файл успешно скачан
//                    Log.d("Download", "Файл скачан в ${file.absolutePath}")
//                    file.absolutePath
//                } catch (e: Exception) {
//                    // Обработка ошибки при скачивании
//                    Log.e("Download", "Ошибка при сохранении файла", e)
//                    null
//                }
//            } else {
//                // Обработка ошибки ответа
//                Log.e("Download", "Ошибка загрузки: ${response.code()}")
//                null
//            }
//        }

    override suspend fun getExcursionByName(name: String): Excursion? {
        TODO("Not yet implemented")
    }
}

private fun Long.toMByte(): Double = this.toDouble() / (1024 * 1024)

private sealed class TypeSearch {
    data class Category(val category: String) : TypeSearch()

    data object New : TypeSearch()

    data object Popular : TypeSearch()

    data class Recommendation(val excursion: Excursion) : TypeSearch()
}