package com.lapoushko.storage.source

import android.util.Log
import com.lapoushko.domain.entity.Excursion
import com.lapoushko.domain.source.ExcursionDataSource
import com.lapoushko.storage.dao.ExcursionDao
import com.lapoushko.storage.mapper.ExcursionDbMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File

/**
 * @author Lapoushko
 */
class ExcursionDataSourceImpl(
    private val dao: ExcursionDao,
    private val mapper: ExcursionDbMapper
) : ExcursionDataSource {
    override suspend fun getSavedExcursion(id: String): Excursion {
        return mapper.toDomain(dao.getSavedExcursion(id))
    }

    override suspend fun deleteExcursion(excursion: Excursion) {
        withContext(Dispatchers.IO){
            excursion.points.forEach { point ->
                deleteFileIfExists(point.image)
                deleteFileIfExists(point.audio)
            }
        }
        dao.deleteExcursion(excursion.id)
    }

    override suspend fun saveExcursion(excursion: Excursion) {
        dao.saveExcursion(mapper.toDb(excursion))
    }

    override fun getSavedExcursions(): Flow<List<Excursion>> =
        dao.getSavedExcursions()
            .map { list ->
                list.map { excursionDb ->
                    mapper.toDomain(excursionDb)
                }
            }

    private fun deleteFileIfExists(path: String) {
        try {
            val file = File(path)
            if (file.exists()) {
                val deleted = file.delete()
                Log.d("Delete", "Файл ${file.name} удалён: $deleted")
            } else {
                Log.w("Delete", "Файл ${file.name} не найден")
            }
        } catch (e: Exception) {
            Log.e("Delete", "Ошибка при удалении файла $path", e)
        }
    }
}