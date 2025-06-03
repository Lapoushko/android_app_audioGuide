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
import com.lapoushko.util.ConnectivityObserver
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ExcursionDetailScreenViewModel(
    private val repository: ExcursionRepository,
    private val mapper: ExcursionMapper,
    private val networkConnectivityManager: ConnectivityObserver
) : ViewModel() {
    private var _state = MutableExcursionDetailScreenState()
    val state = _state as ExcursionDetailScreenState

    init {
        observeInternetStatus()
    }

    private fun observeInternetStatus() {
        networkConnectivityManager.observe().onEach { status ->
            _state.internetStatus = status
        }.launchIn(viewModelScope)
    }

    fun updateIsNeedToShowAuthDialog(value: Boolean){
        _state.isNeedToShowAuthDialog = value
    }

    fun checkIsFavourite(uid: String) {
        repository.getFavoritesExcursion(uid)
            .map { excursions -> excursions.any { it.id == _state.curExcursion.id } }
            .onEach { isFavourite ->
                _state.isFavourite = isFavourite
            }.launchIn(viewModelScope)
    }

    fun saveFavouriteExcursion(excursion: ExcursionItem, uuid: String) {
        viewModelScope.launch {
            repository.saveFavouriteExcursion(mapper.toDomain(excursion), uuid)
            _state.isFavourite = true
        }
    }

    fun deleteFavouriteExcursion(excursion: ExcursionItem, uuid: String){
        viewModelScope.launch {
            repository.deleteFavouriteExcursion(mapper.toDomain(excursion), uuid)
            _state.isFavourite = false
        }
    }

    fun loadInterestingExcursions(excursion: ExcursionItem) {
        repository.getRecommendations(excursion = mapper.toDomain(excursion)).onEach { excursions ->
            _state.interestingExcursion = excursions.map { mapper.toUi(it) }.take(5)
        }.launchIn(viewModelScope)
    }

    fun setCurrentExcursion(excursion: ExcursionItem) {
        _state.curExcursion = excursion
    }

    fun checkIsSaved() {
        repository.getSavedExcursions()
            .map { excursions ->
                excursions.any { it.id == _state.curExcursion.id }
            }
            .onEach { isSaved ->
                _state.isSaved = isSaved
                if (isSaved) {
                    _state.downloadAlertState = DownloadAlertState.DOWNLOADING
                    _state.curExcursion =
                        mapper.toUi(repository.getSavedExcursion(state.curExcursion.id))
                } else {
                    _state.downloadAlertState = DownloadAlertState.CONFIRM_SAVE
                }
            }
            .launchIn(viewModelScope)
    }

    fun onSaveButtonClick() {
        _state.isSaveButtonActive = true
        _state.downloadAlertState = if (_state.isSaved) {
            DownloadAlertState.CONFIRM_DELETE
        } else {
            DownloadAlertState.CONFIRM_SAVE
        }
    }

    fun confirmSave(excursion: ExcursionItem, context: Context) {
        _state.downloadAlertState = DownloadAlertState.DOWNLOADING
        startDownload(excursion, context)
    }

    fun confirmDelete(
        excursion: ExcursionItem,
        context: Context,
        onBackIfFromDao: () -> Unit = {}
    ) {
        viewModelScope.launch {
            repository.deleteExcursion(mapper.toDomain(excursion))
            Toast.makeText(
                context,
                context.getString(R.string.excursion_deleted), Toast.LENGTH_SHORT
            ).show()
            _state.isSaved = false
            _state.downloadAlertState = DownloadAlertState.NONE
            _state.isSaveButtonActive = false
            onBackIfFromDao()
        }
    }

    fun cancelDialog() {
        _state.downloadJob?.cancel()
        _state.downloadJob = null

        _state.downloadAlertState = DownloadAlertState.NONE
        _state.isSaveButtonActive = false
        _state.downloadValues = _state.downloadValues.copy(curValue = 0.0)
    }

    private fun startDownload(excursion: ExcursionItem, context: Context) {
        _state.downloadJob = viewModelScope.launch {
            val domain = mapper.toDomain(excursion)
            val size = repository.getSizeExcursion(domain)

            _state.downloadValues = state.downloadValues.copy(
                curValue = 0.0,
                endValue = size
            )

            var currentProgress = 0.0

            val newExcursion = repository.saveExcursion(domain) {
                currentProgress += it
                _state.downloadValues = _state.downloadValues.copy(curValue = currentProgress)
            }

            if (newExcursion != null) {
                _state.isSaved = true
                Toast.makeText(
                    context,
                    context.getString(R.string.excursion_downloaded), Toast.LENGTH_SHORT
                ).show()
                _state.downloadAlertState = DownloadAlertState.NONE
                _state.isSaveButtonActive = false
                setCurrentExcursion(mapper.toUi(newExcursion))
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.error_downloaded), Toast.LENGTH_SHORT
                ).show()
                _state.downloadAlertState = DownloadAlertState.CONFIRM_SAVE
            }
        }
    }

    private class MutableExcursionDetailScreenState : ExcursionDetailScreenState {
        override var curExcursion: ExcursionItem by mutableStateOf(ExcursionItem())
        override var interestingExcursion: List<ExcursionItem> by mutableStateOf(emptyList())

        override var isSaved: Boolean by mutableStateOf(false)
        override var downloadAlertState: DownloadAlertState by mutableStateOf(DownloadAlertState.NONE)
        override var isFavourite: Boolean by mutableStateOf(false)
        override var isSaveButtonActive: Boolean by mutableStateOf(false)
        override var downloadValues: DownloadValues by mutableStateOf(
            DownloadValues(
                0.0,
                null,
                0.0
            )
        )
        override var downloadJob: Job? by mutableStateOf(null)
        override var internetStatus: ConnectivityObserver.Status by mutableStateOf(
            ConnectivityObserver.Status.UNAVAILABLE
        )
        override var isNeedToShowAuthDialog: Boolean by mutableStateOf(false)
    }
}
