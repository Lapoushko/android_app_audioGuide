package com.lapoushko.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lapoushko.domain.usecase.SubscribeGetCategories
import com.lapoushko.domain.usecase.SubscribeGetExcursionUseCase
import com.lapoushko.feature.mapper.ExcursionMapper
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.CarouselItem
import kotlinx.coroutines.launch

/**
 * @author Lapoushko
 */
class SearchScreenViewModel(
    private val getExcursionsUseCase: SubscribeGetExcursionUseCase,
    private val getCategoriesUseCase: SubscribeGetCategories,
    private val mapper: ExcursionMapper
) : ViewModel() {

    private var _state = MutableSearchScreenState()
    val state = _state as SearchScreenState

    init {
        loadInterestingExcursions()
        loadPopularityExcursions()
        loadCategories()
    }

    private fun loadInterestingExcursions() {
        viewModelScope.launch {
            _state.interesting =
                getExcursionsUseCase.getPopularityExcursions().map { mapper.toUi(it) }
        }
    }

    private fun loadPopularityExcursions() {
        viewModelScope.launch {
            _state.popular = getExcursionsUseCase.getInterestingExcursions().map { mapper.toUi(it) }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _state.categories = getCategoriesUseCase.getCategories().map {
                CarouselItem.Category(it)
            }
        }
    }

    private class MutableSearchScreenState() : SearchScreenState {
        override var interesting: List<ExcursionItem> by mutableStateOf(emptyList())
        override var popular: List<ExcursionItem> by mutableStateOf(emptyList())
        override var categories: List<CarouselItem.Category> by mutableStateOf(emptyList())
    }
}