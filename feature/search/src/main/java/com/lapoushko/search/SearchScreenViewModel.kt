package com.lapoushko.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.CarouselItem

/**
 * @author Lapoushko
 */
class SearchScreenViewModel(): ViewModel() {

    private var _state = MutableSearchScreenState()
    val state = _state as SearchScreenState

    init {
        loadInteresting()
        loadExcursion()
        loadCategories()
    }

    private fun loadInteresting(){
        _state.interesting = List(5){ index ->
            ExcursionItem(
                id = index.toLong(),
                name = "Название $index",
                description = "Описание $index",
                category = "Категория",
                price = "Цена $index",
                distance = "Расстояние $index",
                rating = index.toDouble(),
                countRating = index.toLong(),
            )
        }
    }

    private fun loadExcursion(){
        _state.excursions = state.interesting
    }

    private fun loadCategories(){
        _state.categories = List(5){
            CarouselItem.Category("Категория")
        }
    }

    private class MutableSearchScreenState(): SearchScreenState{
        override var excursions: List<ExcursionItem> by mutableStateOf(emptyList())
        override var interesting: List<ExcursionItem> by mutableStateOf(emptyList())
        override var categories: List<CarouselItem.Category> by mutableStateOf(emptyList())
    }
}