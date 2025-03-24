package com.lapoushko.network.service

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
                    for (document in querySnapshot){
                        val excursion =
                            document.toObject(ExcursionNetwork::class.java).copy(id = document.id)
                        if (typeSearch is TypeSearch.Recommendation){
                            if (typeSearch.excursion.id == document.id){
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

    override suspend fun getSavedExcursions(): List<Excursion> {
        return emptyList()
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

    override suspend fun getExcursionByName(name: String): Excursion? {
        TODO("Not yet implemented")
    }
}

private sealed class TypeSearch {
    data class Category(val category: String) : TypeSearch()

    data object New : TypeSearch()

    data object Popular : TypeSearch()

    data class Recommendation(val excursion: Excursion) : TypeSearch()
}