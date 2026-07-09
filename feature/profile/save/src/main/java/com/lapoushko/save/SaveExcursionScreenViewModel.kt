package com.lapoushko.save

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lapoushko.domain.repo.ExcursionRepository
import com.lapoushko.feature.extension.searchByName
import com.lapoushko.feature.mapper.ExcursionMapper
import com.lapoushko.feature.model.ExcursionItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @author Lapoushko
 */
class SaveExcursionScreenViewModel(
    private val repository: ExcursionRepository,
    private val mapper: ExcursionMapper
) : ViewModel() {
    private var _state = MutableSaveExcursionScreenState()
    val state = _state as SaveExcursionsScreenState

    init {
        loadExcursions()
    }

    private fun loadExcursions() {
        repository.getSavedExcursions().onEach { excursions ->
            _state.excursions = excursions.map { mapper.toUi(it) }
            _state.initialExcursions = state.excursions
        }.launchIn(viewModelScope)
    }

    fun searchByName(text: String) {
        _state.excursions = text.searchByName(state.initialExcursions)
    }

    private class MutableSaveExcursionScreenState : SaveExcursionsScreenState {
        override var initialExcursions: List<ExcursionItem> by mutableStateOf(emptyList())
        override var excursions: List<ExcursionItem> by mutableStateOf(emptyList())
    }
}