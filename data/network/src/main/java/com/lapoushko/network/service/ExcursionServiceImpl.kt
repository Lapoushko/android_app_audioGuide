package com.lapoushko.network.service

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.lapoushko.domain.entity.Excursion
import com.lapoushko.domain.service.ExcursionService
import com.lapoushko.network.entity.ExcursionNetwork
import com.lapoushko.network.entity.ExcursionNetworkV2
import com.lapoushko.network.entity.Point
import com.lapoushko.network.mapper.ExcursionNetworkMapper
import kotlinx.coroutines.tasks.await

/**
 * @author Lapoushko
 */
class ExcursionServiceImpl(
    private val mapper: ExcursionNetworkMapper,
) : ExcursionService {
    private val fireStore: FirebaseFirestore = Firebase.firestore
    override suspend fun getSavedExcursions(): List<Excursion> {
        return try {
            val list = getExcV2()
            println(list)
            val querySnapshot = fireStore.collection("excursions").get().await()
            querySnapshot.map {
                mapper.toDomain(it.toObject(ExcursionNetwork::class.java))
            }
        } catch (e: Exception) {
            println("Error retrieving excursions: ${e.message}")
            emptyList()
        }
    }

    private suspend fun getExcV2(): List<ExcursionNetworkV2> {
        return try {
            val querySnapshot = fireStore.collection("excursions_v.01").get().await()

            querySnapshot.map { document ->
                val excursion =
                    document.toObject(ExcursionNetworkV2::class.java).copy(id = document.id)

                val pointsSnapshot =
                    document.reference.collection("points").orderBy("id").get().await()
                val points = pointsSnapshot.map { it.toObject(Point::class.java) }
                excursion.copy(points = points)
            }
        } catch (e: Exception) {
            println("Error retrieving excursions: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getInterestingExcursions(): List<Excursion> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularityExcursions(): List<Excursion> {
        TODO("Not yet implemented")
    }

    override suspend fun getExcursionsByCategory(category: String): List<Excursion> {
        TODO("Not yet implemented")
    }

    override suspend fun getExcursionByName(name: String): Excursion? {
        TODO("Not yet implemented")
    }
}