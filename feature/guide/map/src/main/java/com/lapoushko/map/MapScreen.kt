package com.lapoushko.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.lapoushko.extension.setPoint
import com.lapoushko.feature.model.PointItem
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@Composable
fun MapScreen(
    previousPoint: PointItem,
    nextPoint: PointItem?,
    viewModel: MapScreenViewModel = koinViewModel()
) {

    val state = viewModel.state
    val cameraPosition = state.cameraPosition
    var previousPosition: Point?
    var nextPosition: Point?

    val currentZoom = state.currentZoom

    val context = LocalContext.current
    val mapView = remember {
        mutableStateOf<MapView?>(null)
    }

    val setPosition = {
        viewModel.setZoom(15f)
        viewModel.setPosition(
            point = previousPoint.point.setPoint(),
            typePosition = TypePosition.PREVIOUS
        )
        nextPoint?.let {
            viewModel.setPosition(point = it.point.setPoint(), typePosition = TypePosition.NEXT)
        } ?: viewModel.setPosition(null, TypePosition.NEXT)
        previousPosition = viewModel.state.previousPosition
        nextPosition = viewModel.state.nextPosition

        println("${previousPosition?.latitude} ${previousPosition?.longitude}")
        println("${nextPosition?.latitude} ${nextPosition?.longitude}")
    }

    AndroidView(factory = { MapView(it) }, modifier = Modifier.fillMaxSize()) {
        mapView.value = it
    }

    LaunchedEffect(Unit) {
        snapshotFlow {
            mapView.value
        }.collect {
            it?.let {
                MapKitFactory.initialize(context)
                MapKitFactory.getInstance().onStart()
                it.onStart()
            }
        }
    }

    LaunchedEffect(previousPoint, nextPoint) {
        setPosition()
    }

    mapView.value?.mapWindow?.map?.move(
        CameraPosition(cameraPosition, currentZoom, 0f, 0f),
        Animation(Animation.Type.SMOOTH, 300f), null
    )
}

@Preview(showBackground = true)
@Composable
private fun MapScreenPreview() {
    MapScreen(
        previousPoint = PointItem("", "", "", point = Pair(0.0, 0.0), ""),
        nextPoint = PointItem("", "", "", point = Pair(0.0, 0.0), ""),
    )
}