package com.lapoushko.domain.entity

/**
 * @author Lapoushko
 */
data class Excursion(
    val id: String,
    val name: String,
    val description: String,
    val categories: List<String>,
    val distance: Long,
    val age: Int,
    val rating: Double,
    val countRating: Long,
    val points: List<Point>,
)

data class Point(
    val name: String,
    val text: String,
    val image: String,
    val point: Pair<Double, Double>,
    val audio: String
)