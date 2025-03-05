package com.lapoushko.network.service

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.lapoushko.domain.service.CategoryService
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author Lapoushko
 */
class CategoryServiceImpl : CategoryService {
    private val fireStore: FirebaseFirestore = Firebase.firestore
    override suspend fun getCategories(): List<String> {
        return suspendCoroutine { continuation ->
            fireStore.collection("categories").get()
                .addOnSuccessListener { value ->
                    val categories =
                        value?.documents?.map { it.getString("naming") ?: "" } ?: emptyList()
                    continuation.resume(categories)
                }
                .addOnFailureListener { error ->
                    println(error)
                    continuation.resumeWithException(error)
                }
        }
    }
}