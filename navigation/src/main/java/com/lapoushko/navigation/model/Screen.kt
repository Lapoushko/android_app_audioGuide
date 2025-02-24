package com.lapoushko.navigation.model

import com.lapoushko.feature.model.ExcursionItem
import kotlinx.serialization.Serializable

/**
 * @author Lapoushko
 */
@Serializable
sealed class Screen {

    @Serializable
    data class Category(val category: String) : Screen()

    @Serializable
    data class ExcursionDetail(val excursion: ExcursionItem) : Screen()
}