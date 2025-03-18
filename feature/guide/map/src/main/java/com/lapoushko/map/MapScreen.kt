package com.lapoushko.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ZoomIn
import androidx.compose.material.icons.outlined.ZoomOut
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.lapoushko.extension.setPoint
import com.lapoushko.feature.model.PointItem
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
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
    var isNewPositions = remember { mutableStateOf(false) }.value

    val state = viewModel.state

    val startDuration = state.startDuration
    val startZooming = state.startZooming
    val cameraPosition = state.cameraPosition
    var previousPosition: Point? = null
    var nextPosition: Point? = null
    val currentZoom = state.currentZoom

    val context = LocalContext.current
    val mapView = remember {
        mutableStateOf<MapView?>(null)
    }

    val zoom = { value: Float ->
        mapView.value?.mapWindow?.map?.move(
            CameraPosition(viewModel.state.previousPosition, value, 0f, 0f),
            Animation(Animation.Type.SMOOTH, startDuration),
            null
        )
    }

    val setPosition = {
        viewModel.setZoom(startZooming)
        viewModel.setPosition(
            point = previousPoint.point.setPoint(),
            typePosition = TypePosition.PREVIOUS
        )
        nextPoint?.let {
            viewModel.setPosition(point = it.point.setPoint(), typePosition = TypePosition.NEXT)
        } ?: viewModel.setPosition(null, TypePosition.NEXT)
        previousPosition = viewModel.state.previousPosition
        nextPosition = viewModel.state.nextPosition

        zoom(startZooming)
        isNewPositions = true
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

    LaunchedEffect(state.currentZoom) {
        zoom(currentZoom)
    }

    if (isNewPositions) {
        isNewPositions = false
        mapView.value.let {
            it!!.mapWindow.map.mapObjects.addPlacemark().apply {
                geometry = Point(50.0, 50.0)
                setIcon(ImageProvider.fromResource(context, R.drawable.place))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { MapView(it) },
            modifier = Modifier.fillMaxSize()
        ) {
            mapView.value = it
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .zIndex(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ZoomingButton(
                onZoom = { viewModel.setZoom(viewModel.state.currentZoom + 1f) },
                type = TypeZooming.PLUS
            )
            ZoomingButton(
                onZoom = { viewModel.setZoom(viewModel.state.currentZoom - 1f) },
                type = TypeZooming.MINUS
            )
        }
    }
}

@Composable
private fun ZoomingButton(
    onZoom: () -> Unit,
    type: TypeZooming,
) {
    SmallFloatingActionButton(
        onClick = {
            onZoom()
        }
    ) {
        when (type) {
            TypeZooming.PLUS -> Icon(imageVector = Icons.Outlined.ZoomIn, contentDescription = null)
            TypeZooming.MINUS -> Icon(
                imageVector = Icons.Outlined.ZoomOut,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MapScreenPreview() {
    MapScreen(
        previousPoint = PointItem("", "", "", point = Pair(0.0, 0.0), ""),
        nextPoint = PointItem("", "", "", point = Pair(0.0, 0.0), ""),
    )
}

@Preview(showBackground = true)
@Composable
private fun ZoomingButtonPreview() {
    ZoomingButton(
        onZoom = { },
        type = TypeZooming.PLUS
    )
}

private enum class TypeZooming() {
    PLUS,
    MINUS
}