package com.lapoushko.domain.entity

/**
 * @author Lapoushko
 */
class Excursion(
    val id: Long,
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val distance: Long,
    val rating: Double,
    val countRating: Long
)