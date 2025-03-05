package com.lapoushko.feature.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * @author Lapoushko
 */
@Serializable
@Parcelize
data class ExcursionItem(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val category: List<String> = emptyList(),
    val price: String = "",
    val distance: String = "",
    val rating: Double = 0.0,
    val countRating: Long = 0,
    val points: List<PointItem> = emptyList(),
) : Parcelable

@Serializable
@Parcelize
data class PointItem(
    val name: String = "",
    val text: String = "",
    val image: String = "",
    val point: Pair<Double, Double> = Pair(0.0, 0.0),
    val audio: String = ""
) : Parcelable