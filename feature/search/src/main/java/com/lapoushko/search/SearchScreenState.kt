package com.lapoushko.search

import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.CarouselItem

/**
 * @author Lapoushko
 */
interface SearchScreenState {
    val excursions: List<ExcursionItem>
    val interesting: List<ExcursionItem>
    val categories: List<CarouselItem.Category>
}