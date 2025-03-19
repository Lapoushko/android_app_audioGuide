package com.lapoushko.map

import android.Manifest
import android.content.Context
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.yandex.mapkit.geometry.Point

/**
 * @author Lapoushko
 */
class MapScreenViewModel : ViewModel() {
    private var _state = MutableMapScreenState()
    val state = _state as MapScreenState

    fun setZoom(zoom: Float) {
        _state.currentZoom = zoom
    }

    fun setPosition(point: Point?, typePosition: TypePosition) {
        when (typePosition) {
            TypePosition.CAMERA -> _state.cameraPosition = point ?: Point(0.0, 0.0)
            TypePosition.PREVIOUS -> _state.previousPosition = point ?: Point(0.0, 0.0)
            TypePosition.NEXT -> _state.nextPosition = point
        }
    }

    private class MutableMapScreenState : MapScreenState {
        override val startDuration: Float by mutableFloatStateOf(0.5f)
        override var currentZoom: Float by mutableFloatStateOf(0f)
        override var previousPosition: Point by mutableStateOf(Point(0.0, 0.0))
        override var nextPosition: Point? by mutableStateOf(null)
        override var cameraPosition: Point? by mutableStateOf(null)
        override val startZooming: Float by mutableFloatStateOf(15f)
        override var permissions: List<String> by mutableStateOf(
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    fun getUserLocation(context: Context, onLocationReceived: (Location) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val locationTask: Task<Location> = fusedLocationClient.lastLocation

        locationTask.addOnSuccessListener { location ->
            if (location != null) {
                onLocationReceived(location)
                _state.cameraPosition = Point(location.latitude, location.longitude)
            }
        }
    }
}

interface MapScreenState {
    val startDuration: Float
    val currentZoom: Float
    val previousPosition: Point
    val nextPosition: Point?
    val cameraPosition: Point?
    val startZooming: Float
    val permissions: List<String>
}

enum class TypePosition {
    CAMERA,
    PREVIOUS,
    NEXT
}