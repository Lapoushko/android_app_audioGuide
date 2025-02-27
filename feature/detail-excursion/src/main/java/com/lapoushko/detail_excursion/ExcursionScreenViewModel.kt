package com.lapoushko.detail_excursion

import androidx.lifecycle.ViewModel
import com.lapoushko.feature.model.ExcursionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExcursionScreenViewModel : ViewModel() {
    private var _state = MutableStateFlow<List<ExcursionItem>>(emptyList())
    val state = _state.asStateFlow()

    init {
        _state.value = List(5){ index ->
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
}
