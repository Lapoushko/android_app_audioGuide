package com.lapoushko.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lapoushko.domain.repo.CategoryRepository
import com.lapoushko.domain.repo.ExcursionRepository
import com.lapoushko.feature.extension.searchByName
import com.lapoushko.feature.mapper.ExcursionMapper
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.CarouselItem
import com.lapoushko.util.ConnectivityObserver
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @author Lapoushko
 */
class SearchScreenViewModel(
    private val excursionRepository: ExcursionRepository,
    private val categoryRepository: CategoryRepository,
    private val mapper: ExcursionMapper,
    private val networkConnectivityManager: ConnectivityObserver
) : ViewModel() {

    private val _state = MutableSearchScreenState()
    val state = _state as SearchScreenState

    init {
        loadCategories()
        loadPopularityExcursions()
        loadInterestingExcursions()
        loadNewExcursions()
        observeInternetStatus()
        loadFromDao()
    }

    private fun loadCategories() {
        categoryRepository.getCategories()
            .onEach { categories ->
                _state.categories = categories.map {
                    CarouselItem.Category(it.name, it.image)
                }
            }.launchIn(viewModelScope)
    }

    private fun loadPopularityExcursions() {
        excursionRepository.getPopularityExcursions().onEach { excursions ->
            _state.populars = excursions.map { mapper.toUi(it) }.take(5)
        }.launchIn(viewModelScope)
    }

    private fun loadInterestingExcursions() {
        excursionRepository.getInterestingExcursions().onEach { excursions ->
            _state.interesting = excursions.map { mapper.toUi(it) }.take(5)
            _state.allInteresting = excursions.map { mapper.toUi(it) }
            _state.initialAllInteresting = state.allInteresting
        }.launchIn(viewModelScope)
    }

    private fun loadNewExcursions() {
        excursionRepository.getNewExcursions().onEach { excursions ->
            _state.news = excursions.map { mapper.toUi(it) }.take(5)
        }.launchIn(viewModelScope)
    }

    private fun observeInternetStatus(){
        networkConnectivityManager.observe().onEach { status ->
            _state.internetStatus = status
        }.launchIn(viewModelScope)
    }

    private fun loadFromDao(){
        excursionRepository.getSavedExcursions().onEach { excursions ->
            _state.excursionFromDao = excursions.map { mapper.toUi(it) }
        }.launchIn(viewModelScope)
    }

    fun searchByName(text: String) {
        _state.allInteresting = text.searchByName(state.initialAllInteresting)
    }

    fun updateIsSearch(value: Boolean) {
        _state.isSearch = value
    }

    private class MutableSearchScreenState : SearchScreenState {
        override var populars: List<ExcursionItem> by mutableStateOf(emptyList())
        override var news: List<ExcursionItem> by mutableStateOf(emptyList())
        override var interesting: List<ExcursionItem> by mutableStateOf(emptyList())
        override var excursionFromDao: List<ExcursionItem> by mutableStateOf(emptyList())

        override var categories: List<CarouselItem.Category> by mutableStateOf(emptyList())

        override var allInteresting: List<ExcursionItem> by mutableStateOf(emptyList())
        override var initialAllInteresting: List<ExcursionItem> by mutableStateOf(emptyList())
        override var isSearch: Boolean by mutableStateOf(false)
        override var internetStatus: ConnectivityObserver.Status by mutableStateOf(
            ConnectivityObserver.Status.UNAVAILABLE)
    }
}