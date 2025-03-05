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
    val countRating: Long,
    val points: List<Point>,
)

class Point(
    val name: String,
    val text: String,
    val image: String,
    val point: Pair<Double, Double>,
    val audio: String
)