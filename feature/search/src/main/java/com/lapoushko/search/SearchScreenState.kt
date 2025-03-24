package com.lapoushko.search

import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.CarouselItem

/**
 * @author Lapoushko
 */
interface SearchScreenState {
    val specialExcursions: List<ExcursionItem>
    val isNew: Boolean
    val interesting: List<ExcursionItem>
    val categories: List<CarouselItem.Category>
}