package com.lapoushko.domain.entity

/**
 * @author Lapoushko
 */
class Excursion(
    val id: String,
    val name: String,
    val description: String,
    val categories: List<String>,
    val price: Double,
    val distance: Long,
    val rating: Double,
    val countRating: Long
)