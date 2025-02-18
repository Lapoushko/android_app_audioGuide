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
    val id: Long,
    val name: String,
    val description: String,
    val category: String,
    val price: String,
    val distance: String,
    val rating: Double,
    val countRating: Long
) : Parcelable