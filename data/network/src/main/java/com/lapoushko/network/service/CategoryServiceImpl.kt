package com.lapoushko.network.service

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.lapoushko.domain.service.CategoryService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * @author Lapoushko
 */
class CategoryServiceImpl : CategoryService {
    private val fireStore: FirebaseFirestore = Firebase.firestore
    override fun getCategories(): Flow<List<String>> = callbackFlow {
        fireStore.collection("categories").get()
            .addOnSuccessListener { value ->
                val categories =
                    value?.documents?.map { it.getString("naming") ?: "" } ?: emptyList()
                trySend(categories)
            }
            .addOnFailureListener { error ->
                println(error)
                trySend(emptyList())
            }
        awaitClose()
    }
}