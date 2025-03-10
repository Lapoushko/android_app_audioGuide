package com.lapoushko.save

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lapoushko.domain.repo.ExcursionRepository
import com.lapoushko.feature.mapper.ExcursionMapper
import com.lapoushko.feature.model.ExcursionItem
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            _state.excursions = repository.getSavedExcursions().map { mapper.toUi(it) }
        }
    }

    private class MutableSaveExcursionScreenState() : SaveExcursionsScreenState {
        override var excursions: List<ExcursionItem> by mutableStateOf(emptyList())
    }
}