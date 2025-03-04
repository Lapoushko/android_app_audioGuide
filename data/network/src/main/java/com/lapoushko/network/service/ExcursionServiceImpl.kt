package com.lapoushko.network.service

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.lapoushko.domain.entity.Excursion
import com.lapoushko.domain.service.ExcursionService
import com.lapoushko.network.entity.ExcursionNetwork
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
            val querySnapshot = fireStore.collection("excursions").get().await()
            querySnapshot.map {
                mapper.toDomain(it.toObject(ExcursionNetwork::class.java))
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