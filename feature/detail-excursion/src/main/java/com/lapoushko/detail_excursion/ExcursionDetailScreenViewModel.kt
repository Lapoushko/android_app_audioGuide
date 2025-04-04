package com.lapoushko.detail_excursion

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lapoushko.domain.repo.ExcursionRepository
import com.lapoushko.feature.mapper.ExcursionMapper
import com.lapoushko.feature.model.ExcursionItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ExcursionDetailScreenViewModel(
    private val repository: ExcursionRepository,
    private val mapper: ExcursionMapper
) : ViewModel() {
    private var _state = MutableExcursionDetailScreenState()
    val state = _state as ExcursionDetailScreenState

    fun loadInterestingExcursions(excursion: ExcursionItem) {
        repository.getRecommendations(excursion = mapper.toDomain(excursion)).onEach { excursions ->
            _state.interestingExcursion = excursions.map { mapper.toUi(it) }.take(5)
        }.launchIn(viewModelScope)
    }

    fun setCurrentExcursion(excursion: ExcursionItem) {
        _state.curExcursion = excursion
        checkIsSaved()
    }

    fun setIsSavedButtonActive(value: Boolean) {
        _state.isSaveButtonActive = value
    }

    fun setDownloadAlertState(value: DownloadAlertState) {
        _state.downloadAlertState = value
    }

    fun clearTimer(){
        _state.downloadValues = state.downloadValues.copy(curValue = state.downloadValues.startValue)
    }

    private fun checkIsSaved() {
        repository.getSavedExcursions()
            .map { excursions ->
                excursions.any { it.id == _state.curExcursion.id }
            }
            .onEach { isSaved ->
                _state.isSaved = isSaved
                _state.downloadAlertState =
                    if (isSaved) DownloadAlertState.DELETING else DownloadAlertState.SAVING
            }
            .launchIn(viewModelScope)
    }

    fun saveExcursion(excursion: ExcursionItem, context: Context) {
        viewModelScope.launch {
            val excursionDomain = mapper.toDomain(excursion)
            val size = repository.getSizeExcursion(excursionDomain)
            _state.downloadValues = state.downloadValues.copy(endValue = size)
            Toast.makeText(context, "Save excursion $size", Toast.LENGTH_LONG).show()
            repository.saveExcursion(excursionDomain)
        }
    }

    fun deleteExcursion(excursion: ExcursionItem, context: Context) {
        viewModelScope.launch {
            Toast.makeText(context, "Delete excursion", Toast.LENGTH_LONG).show()
            repository.deleteExcursion(mapper.toDomain(excursion))
        }
    }

    private class MutableExcursionDetailScreenState : ExcursionDetailScreenState {
        override var curExcursion: ExcursionItem by mutableStateOf(ExcursionItem())
        override var interestingExcursion: List<ExcursionItem> by mutableStateOf(emptyList())
        override var isSaved: Boolean by mutableStateOf(false)
        override var downloadAlertState: DownloadAlertState by mutableStateOf(DownloadAlertState.EMPTY)
        override var isFavourite: Boolean by mutableStateOf(false)
        override var isSaveButtonActive: Boolean by mutableStateOf(false)
        override var downloadValues: DownloadValues by mutableStateOf(DownloadValues(0.0, 100000.0, 0.0))
    }
}
