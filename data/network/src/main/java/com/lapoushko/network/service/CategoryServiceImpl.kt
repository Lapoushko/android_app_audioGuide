package com.lapoushko.network.service

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.lapoushko.domain.service.CategoryService
import kotlinx.coroutines.tasks.await

/**
 * @author Lapoushko
 */
class CategoryServiceImpl : CategoryService {
    private val fireStore: FirebaseFirestore = Firebase.firestore
    override suspend fun getCategories(): List<String> {
        return try {
            val querySnapshot = fireStore.collection("categories").get().await()
            querySnapshot.map { it.getString("naming") ?: "" }
        } catch (e: Exception) {
            println("Error retrieving excursions: ${e.message}")
            emptyList()
        }
    }
}