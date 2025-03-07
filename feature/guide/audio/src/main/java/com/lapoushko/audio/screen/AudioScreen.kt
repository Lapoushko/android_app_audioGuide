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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import coil3.compose.AsyncImage
import com.lapoushko.feature.model.ExcursionItem
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
    viewModel: AudioScreenViewModel = koinViewModel()
) {
    val pagerState = rememberPagerState { excursion.points.size }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val playlist = excursion.points.map { PlaylistItem(it.text, it.audio) }
        viewModel.preparePlayer(context = context, playlist)
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
            ) { page ->
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = excursion.points.getOrNull(page)?.image
                            ?: R.drawable.example, //excursion.images.getOrNull(page)
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    DescriptionText(text = excursion.points[page].text)
                }
            }

            AudioPlayerControl(
                isPlaying = viewModel.state.isPlaying,
                onPlayPause = { viewModel.updatePlaylist(ControlButtons.PLAY) },
                onNext = {
                    if (viewModel.state.currentIndex != excursion.points.size - 1) viewModel.updatePlaylist(ControlButtons.NEXT)
                    scope.launch {
                        pagerState.animateScrollToPage((pagerState.currentPage + 1).coerceAtMost(excursion.points.size - 1))
                    }
                },
                onPrevious = {
                    if (viewModel.state.currentIndex != 0) viewModel.updatePlaylist(ControlButtons.PREVIOUS)
                    scope.launch {
                        pagerState.animateScrollToPage((pagerState.currentPage - 1).coerceAtLeast(0))
                    }
                }
            )
        }
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
            .height(200.dp)
            .verticalScroll(rememberScrollState()),
        text = text,
        style = Typography.titleMedium,
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun AudioPlayerControl(
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Column {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..1f,
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
            Text(text = "00:00", style = Typography.bodyMedium, color = Color.White)

            PlayerButton(imageVector = Icons.Filled.SkipPrevious, onClick = onPrevious)
            PlayerButton(
                imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                onClick = onPlayPause
            )
            PlayerButton(imageVector = Icons.Filled.SkipNext, onClick = onNext)

            Text(text = "02:00", style = Typography.bodyMedium, color = Color.White)
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

@Preview(showBackground = true)
@Composable
private fun AudioScreenPreview() {
    AudioScreen(
        excursion = ExcursionItem(
            "",
            "Название",
            "Описание",
            listOf("Категория"),
            "Бесплатно",
            "1.2км",
            2.5,
            1,
        )
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun  RequestNotificationPermissions () {
    var hasNotificationPermission by remember { mutableStateOf( false ) }
    val permissionResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { hasNotificationPermission = it }
    )
    LaunchedEffect(key1 = true ) {
        permissionResult.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}