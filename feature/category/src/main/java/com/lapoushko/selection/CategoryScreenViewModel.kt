package com.lapoushko.selection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lapoushko.domain.repo.ExcursionRepository
import com.lapoushko.feature.mapper.ExcursionMapper
import com.lapoushko.feature.model.ExcursionItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @author Lapoushko
 */
class CategoryScreenViewModel(
    private val repository: ExcursionRepository,
    private val mapper: ExcursionMapper
) : ViewModel() {
    private var _state = MutableCategoryScreenState()
    val state = _state as CategoryScreenState

    init {
        println("косяк")
    }

    fun loadExcursions(category: String) {
        repository.getExcursionsByCategory(category).onEach { excursions ->
            _state.excursions = excursions.map { mapper.toUi(it) }
        }.launchIn(viewModelScope)
    }

    private class MutableCategoryScreenState() : CategoryScreenState {
        override var excursions: List<ExcursionItem> by mutableStateOf(emptyList())
    }
}