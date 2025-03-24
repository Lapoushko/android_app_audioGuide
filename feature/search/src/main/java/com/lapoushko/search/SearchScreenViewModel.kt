package com.lapoushko.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lapoushko.domain.repo.CategoryRepository
import com.lapoushko.domain.repo.ExcursionRepository
import com.lapoushko.feature.mapper.ExcursionMapper
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.CarouselItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * @author Lapoushko
 */
class SearchScreenViewModel(
    private val excursionRepository: ExcursionRepository,
    private val categoryRepository: CategoryRepository,
    private val mapper: ExcursionMapper
) : ViewModel() {

    private val _state = MutableSearchScreenState()
    val state = _state as SearchScreenState

    init {
        loadCategories()
        loadPopularityExcursions()
        loadInterestingExcursions()
    }

    private fun loadPopularityExcursions() {
        viewModelScope.launch {
            _state.specialExcursions = excursionRepository.getPopularityExcursions().map { mapper.toUi(it) }
        }
    }

    private fun loadNewExcursions() {
        viewModelScope.launch {
            _state.specialExcursions = emptyList()
        }
    }

    private fun loadInterestingExcursions() {
        excursionRepository.getInterestingExcursions().onEach { excursions ->
            _state.interesting = excursions.map { mapper.toUi(it) }
        }.launchIn(viewModelScope)
    }

    private fun loadCategories() {
        categoryRepository.getCategories()
            .onEach { categories ->
                _state.categories = categories.map {
                    CarouselItem.Category(it)
                }
            }.launchIn(viewModelScope)
    }

    fun setIsNewExcursions(value: Boolean){
        _state.isNew = value
        if (state.isNew){
            loadNewExcursions()
        } else loadPopularityExcursions()
    }

    private class MutableSearchScreenState : SearchScreenState {
        override var specialExcursions: List<ExcursionItem> by mutableStateOf(emptyList())
        override var isNew: Boolean by mutableStateOf(false)
        override var interesting: List<ExcursionItem> by mutableStateOf(emptyList())
        override var categories: List<CarouselItem.Category> by mutableStateOf(emptyList())
    }
}