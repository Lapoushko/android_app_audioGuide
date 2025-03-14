package com.lapoushko.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Point

/**
 * @author Lapoushko
 */
class MapScreenViewModel : ViewModel() {
    private var _state = MutableMapScreenState()
    val state = _state as MapScreenState

    fun setZoom(zoom: Float){
        _state.currentZoom = zoom
    }

    fun setPosition(point: Point?, typePosition: TypePosition){
        when(typePosition){
            TypePosition.CAMERA -> _state.cameraPosition = point ?: Point(0.0,0.0)
            TypePosition.PREVIOUS -> _state.previousPosition = point ?: Point(0.0,0.0)
            TypePosition.NEXT -> _state.nextPosition = point
        }
    }

    private class MutableMapScreenState : MapScreenState{
        override var currentZoom: Float by mutableFloatStateOf(0f)
        override var previousPosition: Point by mutableStateOf(Point(0.0,0.0))
        override var nextPosition: Point? by mutableStateOf(null)
        override var cameraPosition: Point by mutableStateOf(Point(0.0,0.0))
    }
}

interface MapScreenState{
    val currentZoom: Float
    val previousPosition: Point
    val nextPosition: Point?
    val cameraPosition: Point
}

enum class TypePosition {
    CAMERA,
    PREVIOUS,
    NEXT
}