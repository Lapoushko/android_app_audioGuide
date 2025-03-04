@file:OptIn(ExperimentalMaterial3Api::class)

package com.lapoushko.audio

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.R
import com.lapoushko.ui.theme.Typography
import com.lapoushko.ui.theme.onPrimaryLight
import com.lapoushko.ui.theme.primaryLight
import com.lapoushko.ui.theme.secondaryContainerLight

/**
 * @author Lapoushko
 */
@Composable
fun AudioScreen(
    excursion: ExcursionItem
) {
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImageWithOverlay(excursion.images.firstOrNull())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            DescriptionText(text = excursion.description)
            AudioPlayerControl()
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
                .background(Color.Black.copy(alpha = 0.5f))
        )
    }
}

@Composable
private fun DescriptionText(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .height(200.dp),
        text = text,
        style = Typography.titleMedium,
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun AudioPlayerControl() {
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

            PlayerButton(imageVector = Icons.Filled.SkipPrevious, onClick = { /*TODO*/ })
            PlayerButton(imageVector = Icons.Filled.PlayArrow, onClick = { /*TODO*/ })
            PlayerButton(imageVector = Icons.Filled.SkipNext, onClick = { /*TODO*/ })

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
            emptyList(),
            emptyList()
        )
    )
}