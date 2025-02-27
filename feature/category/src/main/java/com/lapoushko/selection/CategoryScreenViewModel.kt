package com.lapoushko.selection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lapoushko.domain.usecase.SubscribeGetExcursionUseCase
import com.lapoushko.feature.mapper.ExcursionMapper
import com.lapoushko.feature.model.ExcursionItem
import kotlinx.coroutines.launch

/**
 * @author Lapoushko
 */
class CategoryScreenViewModel(
    private val getUseCase: SubscribeGetExcursionUseCase,
    private val mapper: ExcursionMapper
) : ViewModel() {
    private var _state = MutableCategoryScreenState()
    val state = _state as CategoryScreenState

    fun loadExcursions(category: String) {
        viewModelScope.launch {
            _state.excursions = getUseCase.getExcursionsByCategory(category = category)
                .map { mapper.toUi(it) }
        }
    }

    private class MutableCategoryScreenState() : CategoryScreenState {
        override var excursions: List<ExcursionItem> by mutableStateOf(emptyList())
    }
}