package com.lapoushko.network.service

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.lapoushko.domain.entity.Excursion
import com.lapoushko.domain.service.ExcursionService
import com.lapoushko.network.entity.ExcursionNetwork
import com.lapoushko.network.mapper.ExcursionNetworkMapper
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author Lapoushko
 */
class ExcursionServiceImpl(
    private val mapper: ExcursionNetworkMapper,
) : ExcursionService {
    private val fireStore: FirebaseFirestore = Firebase.firestore
    override suspend fun getSavedExcursions(): List<Excursion> {
        return suspendCoroutine { continuation ->
            fireStore.collection("excursions").get()
                .addOnSuccessListener { value ->
                    val excursions = value?.documents?.map {
                        mapper.toDomain(it.toObject(ExcursionNetwork::class.java)!!)
                    } ?: emptyList()
                    continuation.resume(excursions)
                }
                .addOnFailureListener { error ->
                    println(error)
                    continuation.resumeWithException(error)
                }
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