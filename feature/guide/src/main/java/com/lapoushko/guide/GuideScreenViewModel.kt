package com.lapoushko.guide

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * @author Lapoushko
 */
class GuideScreenViewModel : ViewModel() {
    private var _state = MutableGuideScreenState()
    val state = _state as GuideScreenState

    fun updateIndex(index: Int){
        _state.indexCurrentScreen = index
    }

    private class MutableGuideScreenState : GuideScreenState{
        override var indexCurrentScreen: Int by mutableIntStateOf(0)
    }
}

interface GuideScreenState{
    val indexCurrentScreen: Int
}