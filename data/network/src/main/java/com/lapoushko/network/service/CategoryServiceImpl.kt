package com.lapoushko.network.service

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.lapoushko.domain.entity.Category
import com.lapoushko.domain.service.CategoryService
import com.lapoushko.network.entity.CategoryNetwork
import com.lapoushko.network.mapper.CategoryNetworkMapper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * @author Lapoushko
 */
class CategoryServiceImpl(
    private val mapper: CategoryNetworkMapper
) : CategoryService {
    private val fireStore: FirebaseFirestore = Firebase.firestore
    override fun getCategories(): Flow<List<Category>> = callbackFlow {
        fireStore.collection("categories").get()
            .addOnSuccessListener { querySnapshot ->
                val categories = mutableListOf<Category>()
                querySnapshot?.forEach { document ->
                    val category =
                        document.toObject(CategoryNetwork::class.java)
                    categories.add(mapper.toDomain(category))
                }
                trySend(categories)
            }
            .addOnFailureListener { error ->
                println(error)
                trySend(emptyList())
            }
        awaitClose()
    }
}