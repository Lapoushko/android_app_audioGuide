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
    val images: List<String> = emptyList(),
    val points: List<Pair<Double, Double>> = emptyList(),
    val audio: List<String> = emptyList(),
    val texts: List<String> = emptyList(),
    val namesPoints: List<String> = emptyList()
) : Parcelable