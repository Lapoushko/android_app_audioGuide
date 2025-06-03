package com.lapoushko.favourite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lapoushko.domain.repo.ExcursionRepository
import com.lapoushko.feature.extension.searchByName
import com.lapoushko.feature.mapper.ExcursionMapper
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.util.ConnectivityObserver
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @author Lapoushko
 */
class FavouriteScreenViewModel(
    private val repository: ExcursionRepository,
    private val mapper: ExcursionMapper,
    private val networkConnectivityManager: ConnectivityObserver
) : ViewModel() {
    private var _state = MutableFavouriteScreenState()
    val state = _state as FavouriteScreenState

    init {
        observeInternetStatus()
        println("косяк")
    }

    fun loadExcursions(uuid: String){
        repository.getFavoritesExcursion(uuid).onEach { excursions ->
            _state.excursions = excursions.map { mapper.toUi(it) }
            _state.initialExcursions = state.excursions
        }.launchIn(viewModelScope)
    }

    private fun observeInternetStatus(){
        networkConnectivityManager.observe().onEach { status ->
            _state.internetStatus = status
        }.launchIn(viewModelScope)
    }


    fun searchByName(text: String) {
        _state.excursions = text.searchByName(state.initialExcursions)
    }

    private class MutableFavouriteScreenState(): FavouriteScreenState{
        override var initialExcursions: List<ExcursionItem> by mutableStateOf(emptyList())
        override var excursions: List<ExcursionItem> by mutableStateOf(emptyList())
        override var internetStatus: ConnectivityObserver.Status by mutableStateOf(ConnectivityObserver.Status.UNAVAILABLE)
    }
}

interface FavouriteScreenState{
    val initialExcursions: List<ExcursionItem>
    val excursions: List<ExcursionItem>
    val internetStatus: ConnectivityObserver.Status
}