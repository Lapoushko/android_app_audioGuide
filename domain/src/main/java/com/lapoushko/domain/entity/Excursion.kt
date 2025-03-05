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
    val images: List<String>,
    val points: List<Pair<Double, Double>>,
    val texts: List<String>,
    val namesPoints: List<String>,
    val audio: List<String>
)