package com.lapoushko.feature.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lapoushko.domain.entity.User
import com.lapoushko.domain.repo.UserRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

/**
 * @author Lapoushko
 */
class AuthHelperViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private var _state = MutableAuthHelperState()
    val state = _state as AuthHelperState

    init {
        getUser()
    }

    private fun getUser(){
        userRepository.getUser().onEach { user ->
            user?.let {
                _state.user = user
                _state.isNeedToShowAuth = false
            } ?: run {
                _state.user = null
                _state.isNeedToShowAuth = true
            }
        }.launchIn(viewModelScope)
    }

    fun updateIsNeedToShowAuth(value: Boolean){
        _state.isNeedToShowAuth = value
    }

    fun signUpUser(email: String, password: String) {
        userRepository.signUpUser(email, password).onEach { response ->
            _state.response = response
            when(state.response){
                UserRepository.AuthResponse.Success ->{
                    _state.isNeedToShowAuth = false
                }
                else -> {
                    _state.isNeedToShowAuth = true
                }
            }
            getUser()
        }.launchIn(viewModelScope)
    }

    fun signInUser(email: String, password: String) {
        _state.isLoading = true
        userRepository.signInUser(email, password).onEach { response ->
            _state.response = response
            when(state.response){
                UserRepository.AuthResponse.Success ->{
                    updateIsCorrectSignIn(true)
                    _state.isNeedToShowAuth = false
                }
                else -> {
                    updateIsCorrectSignIn(false)
                    _state.isNeedToShowAuth = true
                }
            }
            getUser()
        }.onCompletion {
            _state.isLoading = false
        }
            .launchIn(viewModelScope)
    }

    private fun updateIsCorrectSignIn(value: Boolean) {
        _state.isCorrectSignIn = value
    }

    private class MutableAuthHelperState: AuthHelperState{
        override var user: User? by mutableStateOf(null)
        override var response: UserRepository.AuthResponse? by mutableStateOf(null)
        override var isLoading: Boolean by mutableStateOf(false)
        override var isNeedToShowAuth: Boolean by mutableStateOf(true)
        override var isCorrectSignIn: Boolean by mutableStateOf(true)
    }
}
interface AuthHelperState{
    val user: User?
    val response: UserRepository.AuthResponse?
    val isLoading: Boolean
    val isNeedToShowAuth: Boolean
    val isCorrectSignIn: Boolean
}