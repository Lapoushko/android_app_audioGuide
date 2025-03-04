package com.lapoushko.network.entity

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint

/**
 * @author Lapoushko
 */
data class ExcursionNetwork(
    val id: String? = "",
    val name: String? = "",
    val description: String? = "",
    val distance: Long? = null,
    val categories: List<String>? = null,
    val countRating: Long? = null,
    val price: Double? = null,
    val rating: Double? = null,
    val images: List<String>? = null,
    val points: List<GeoPoint>? = null
)