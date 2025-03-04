package com.lapoushko.detail_excursion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lapoushko.feature.model.ExcursionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExcursionScreenViewModel : ViewModel() {
    private var _state = MutableExcursionScreenState()
    val state = _state as ExcursionScreenState

    init {
        loadInterestingExcursions()
    }

    fun setCurrentExcursion(excursion: ExcursionItem){
        _state.curExcursion = excursion
    }

    private fun loadInterestingExcursions(){
        _state.interestingExcursion = List(5){ index ->
            ExcursionItem(
                id = "",
                name = "Название $index",
                description = "Описание $index",
                category = listOf("Категория"),
                price = "Цена $index",
                distance = "Расстояние $index",
                rating = index.toDouble(),
                countRating = index.toLong(),
                images = emptyList(),
                points = emptyList()
            )
        }
    }

    private class MutableExcursionScreenState(): ExcursionScreenState{
        override var curExcursion: ExcursionItem by mutableStateOf(ExcursionItem())
        override var interestingExcursion: List<ExcursionItem> by mutableStateOf(emptyList())

    }
}
