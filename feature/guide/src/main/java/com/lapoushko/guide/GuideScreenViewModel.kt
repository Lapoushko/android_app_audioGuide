package com.lapoushko.guide

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lapoushko.audio.screen.ObserverAudio

/**
 * @author Lapoushko
 */
class GuideScreenViewModel(
    private val observerAudio: ObserverAudio
) : ViewModel() {
    private var _state = MutableGuideScreenState()
    val state = _state as GuideScreenState

    fun updateIndex(index: Int){
        _state.indexCurrentScreen = index
    }

    fun updateIsDestroy(value: Boolean){
        observerAudio.updateIsDestroy(value)
    }

    private class MutableGuideScreenState : GuideScreenState{
        override var indexCurrentScreen: Int by mutableIntStateOf(0)
        override var isDestroy: Boolean by mutableStateOf(false)
    }
}

interface GuideScreenState{
    val indexCurrentScreen: Int
    val isDestroy: Boolean
}