package com.lapoushko.audio.screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * @author Lapoushko
 */
class ObserverAudio {
    private val _isDestroyFlow = MutableStateFlow(false)
    val isDestroyFlow: StateFlow<Boolean> = _isDestroyFlow.asStateFlow()

    fun updateIsDestroy(value: Boolean) {
        _isDestroyFlow.value = value
    }
}