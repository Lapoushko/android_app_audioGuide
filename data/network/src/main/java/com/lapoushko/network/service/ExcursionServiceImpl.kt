package com.lapoushko.network.service

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.lapoushko.domain.entity.Excursion
import com.lapoushko.domain.service.ExcursionService
import com.lapoushko.network.entity.ExcursionNetwork
import com.lapoushko.network.entity.Point
import com.lapoushko.network.mapper.ExcursionNetworkMapper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * @author Lapoushko
 */
class ExcursionServiceImpl(
    private val mapper: ExcursionNetworkMapper,
) : ExcursionService {
    private val fireStore: FirebaseFirestore = Firebase.firestore
    override suspend fun getSavedExcursions(): List<Excursion> {
        return emptyList()
    }

    override fun getInterestingExcursions(): Flow<List<Excursion>> = callbackFlow {
        fireStore.collection("excursions_v.01").get()
            .addOnSuccessListener { querySnapshot ->
                val excursions = mutableListOf<Excursion>()
                querySnapshot.forEach { document ->
                    val excursion = document.toObject(ExcursionNetwork::class.java).copy(id = document.id)

                    document.reference.collection("points").orderBy("id").get()
                        .addOnSuccessListener { pointsSnapshot ->
                            val points = pointsSnapshot.map { it.toObject(Point::class.java) }
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

    override suspend fun getPopularityExcursions(): List<Excursion> {
        return emptyList()
    }

    override suspend fun getExcursionsByCategory(category: String): List<Excursion> {
        TODO("Not yet implemented")
    }

    override suspend fun getExcursionByName(name: String): Excursion? {
        TODO("Not yet implemented")
    }
}