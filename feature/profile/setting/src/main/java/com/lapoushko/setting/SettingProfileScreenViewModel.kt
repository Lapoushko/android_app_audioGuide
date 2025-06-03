package com.lapoushko.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lapoushko.domain.entity.User
import com.lapoushko.domain.repo.UserRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @author Lapoushko
 */
class SettingProfileScreenViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private var _state = MutableSettingProfileScreenState()
    val state = _state as SettingProfileScreenState

    init {
        getUser()
    }

    private fun getUser() {
        userRepository.getUser().onEach { user ->
            user?.let {
                _state.user = it
                _state.dialogState = SettingProfileScreenState.DialogState.EMPTY
            }
        }.launchIn(viewModelScope)
    }

    fun signOutUser() {
        userRepository.signOutUser()
        _state.user = null
    }

    fun updateIsNeedToShowAuthDialog(value: SettingProfileScreenState.DialogState) {
        _state.dialogState = value
    }

    private class MutableSettingProfileScreenState : SettingProfileScreenState {
        override var user: User? by mutableStateOf(null)
        override var response: UserRepository.AuthResponse? by mutableStateOf(null)
        override var isLoading: Boolean by mutableStateOf(false)
        override var dialogState: SettingProfileScreenState.DialogState by mutableStateOf(
            SettingProfileScreenState.DialogState.AUTH
        )
        override var isCorrectSignIn: Boolean by mutableStateOf(true)
    }
}