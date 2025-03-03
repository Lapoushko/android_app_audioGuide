package com.lapoushko.network.entity

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
    val rating: Double? = null
)