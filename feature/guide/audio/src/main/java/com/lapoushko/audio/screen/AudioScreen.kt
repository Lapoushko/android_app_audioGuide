@file:OptIn(ExperimentalMaterial3Api::class)

package com.lapoushko.audio.screen

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import coil3.compose.AsyncImage
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.PermissionCheck
import com.lapoushko.ui.R
import com.lapoushko.ui.theme.Typography
import com.lapoushko.ui.theme.onPrimaryLight
import com.lapoushko.ui.theme.primaryLight
import com.lapoushko.ui.theme.secondaryContainerLight
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun AudioScreen(
    excursion: ExcursionItem,
    viewModel: AudioScreenViewModel = koinViewModel(),
    onNext: (Int) -> Unit,
    onBack: (Int) -> Unit,
    onClickCancel: () -> Unit
) {
    val state = viewModel.state
    val pagerState = rememberPagerState { excursion.points.size }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val currentPosition = state.currentPosition

    PermissionCheck(Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(Unit) {
        if (state.excursion != excursion) {
            viewModel.setExcursion(excursion)
            val playlist = excursion.points.map { PlaylistItem(it.text, it.audio) }
            viewModel.preparePlayer(context = context, playlist)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val currentImage = excursion.points.getOrNull(pagerState.currentPage)?.image
        BackgroundImageWithOverlay(currentImage)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = excursion.points[page].name,
                        style = Typography.titleLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    AsyncImage(
                        model = excursion.points.getOrNull(page)?.image
                            ?: R.drawable.example,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    DescriptionText(text = excursion.points[page].text)
                }
            }

            AudioPlayerControl(
                currentPosition = currentPosition,
                totalDurationInMS = state.totalDurationInMS,
                isPlaying = state.isPlaying,
                onPlayPause = { viewModel.updatePlaylist(ControlButtons.PLAY) },
                onNext = {
                    if (state.currentIndex != excursion.points.size - 1) {
                        viewModel.updatePlaylist(ControlButtons.NEXT)
                        onNext(state.currentIndex)
                    } else{
                        viewModel.updatesNeedToShowRateDialog(true)
                    }
                    scope.launch {
                        pagerState.animateScrollToPage(
                            (pagerState.currentPage + 1).coerceAtMost(
                                excursion.points.size - 1
                            )
                        )
                    }
                },
                onPrevious = {
                    if (state.currentIndex != 0) {
                        viewModel.updatePlaylist(ControlButtons.PREVIOUS)
                        onBack(state.currentIndex)
                    }
                    scope.launch {
                        pagerState.animateScrollToPage((pagerState.currentPage - 1).coerceAtLeast(0))
                    }
                },
                onSeekTo = { viewModel.updatePlayerPosition(it) }
            )
        }
    }
    if (state.isNeedToShowRateDialog) {
        RateDialog(
            startRate = state.rate,
            onClickCancel = {
                viewModel.updatesNeedToShowRateDialog(false)
                onClickCancel()
            },
            onClickRate = {
                viewModel.updateRate(it)
                viewModel.updatesNeedToShowRateDialog(false)
                onClickCancel()
                /*TODO*/
            }
        )
    }
}

@Composable
private fun BackgroundImageWithOverlay(image: String?) {
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = image ?: R.drawable.example,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
        )
    }
}

@Composable
private fun DescriptionText(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .verticalScroll(rememberScrollState()),
        text = text,
        style = Typography.titleMedium,
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun AudioPlayerControl(
    currentPosition: Long,
    isPlaying: Boolean,
    totalDurationInMS: Long,
    onPlayPause: () -> Unit,
    onSeekTo: (Long) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(currentPosition.toFloat()) }

    LaunchedEffect(currentPosition) {
        sliderPosition = currentPosition.toFloat()
    }

    Column {
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onSeekTo(sliderPosition.toLong())
            },
            valueRange = 0f..totalDurationInMS.toFloat(),
            colors = SliderDefaults.colors(
                thumbColor = onPrimaryLight,
                inactiveTrackColor = secondaryContainerLight,
                activeTrackColor = secondaryContainerLight
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatTime(currentPosition),
                style = Typography.bodyMedium,
                color = Color.White
            )

            PlayerButton(imageVector = Icons.Filled.SkipPrevious, onClick = {
                onPrevious()
                sliderPosition = 0f
            })
            PlayerButton(
                imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                onClick = onPlayPause
            )
            PlayerButton(imageVector = Icons.Filled.SkipNext, onClick = {
                onNext()
                sliderPosition = 0f
            })

            Text(
                text = formatTime(totalDurationInMS),
                style = Typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@Composable
private fun PlayerButton(
    onClick: () -> Unit,
    imageVector: ImageVector
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(Color.White, shape = CircleShape)
            .size(48.dp)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Вперёд",
            tint = primaryLight
        )
    }
}

@Composable
private fun RateDialog(
    startRate: Int,
    onClickRate: (Int) -> Unit,
    onClickCancel: () -> Unit
) {
    AlertDialog(onDismissRequest = {},
        title = {
            Text(
                stringResource(com.lapoushko.audio.R.string.rate),
                style = Typography.titleMedium
            )
        },
        confirmButton = {
        },
        dismissButton = {
            TextButton(onClick = onClickCancel) {
                Text(stringResource(R.string.skip), style = Typography.labelMedium)
            }
        },
        text = {
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(5) { i ->
                    val color = if (startRate > i) primaryLight else Color.Gray
                    IconButton(onClick = {
                        onClickRate(i + 1)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = color
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AudioScreenPreview() {
    AudioScreen(
        excursion = ExcursionItem(
            "",
            "Название",
            "Описание",
            listOf("Категория"),
            "1.2км",
            age = "0+",
            2.5,
            1,
        ),
        onNext = {},
        onBack = {},
        onClickCancel = {}
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestNotificationPermissions() {
    var hasNotificationPermission by remember { mutableStateOf(false) }
    val permissionResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { hasNotificationPermission = it }
    )
    LaunchedEffect(key1 = true) {
        permissionResult.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}

private fun formatTime(milliseconds: Long): String {
    val totalSeconds = (milliseconds / 1000).toInt()
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}