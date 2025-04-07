package com.lapoushko.search

import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.CarouselItem
import com.lapoushko.util.ConnectivityObserver

/**
 * @author Lapoushko
 */
interface SearchScreenState {
    val populars: List<ExcursionItem>
    val news: List<ExcursionItem>
    val interesting: List<ExcursionItem>
    val excursionFromDao: List<ExcursionItem>
    val categories: List<CarouselItem.Category>
    val allInteresting: List<ExcursionItem>
    val initialAllInteresting: List<ExcursionItem>
    val isSearch: Boolean

    val internetStatus: ConnectivityObserver.Status
}