package com.lapoushko.network.entity

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
    val points: List<Point>? = null
)

data class Point(
    val name: String? = null,
    val text: String? = null,
    val image: String? = null,
    val point: GeoPoint? = null,
    val audio: String? = null
)