package com.lapoushko.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.BookmarkRemove
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.FileDownloadOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.lapoushko.ui.theme.Typography

/**
 * @author Lapoushko
 */
@Composable
fun CustomTopAppBar(
    image: String?,
    onClickBack: () -> Unit,
    saveState: NavigationIcon? = null,
    favouriteState: NavigationIcon? = null,
    text: String,
    modifier: Modifier = Modifier
) {
    var isSaved by remember { mutableStateOf(saveState?.isActive ?: false) }
    var isFavored by remember { mutableStateOf(favouriteState?.isActive ?: false) }
    Box {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp)),
            model = image ?: R.drawable.example,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                    )
                ),
        )

        Text(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 60.dp)
                .align(Alignment.BottomStart),
            text = text,
            style = Typography.headlineSmall,
            color = Color.White
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onClickBack() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description",
                )
            }
            Row {
                saveState?.let {
                    IconButton(onClick = {
                        if (isSaved) {
                            saveState.onDeactivate()
                        } else {
                            saveState.onActivate()
                        }
                        isSaved = !isSaved
                    }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = if (isSaved) Icons.Outlined.FileDownloadOff else Icons.Outlined.FileDownload,
                            contentDescription = "Сохранить",
                        )
                    }
                }
                favouriteState?.let {
                    IconButton(onClick = {
                        if (isFavored) {
                            favouriteState.onDeactivate()
                        } else {
                            favouriteState.onActivate()
                        }
                        isFavored = !isFavored
                    }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = if (isFavored) Icons.Outlined.BookmarkRemove else Icons.Outlined.BookmarkAdd,
                            contentDescription = "Избранное",
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomTopAppBarPreview() {
    CustomTopAppBar(
        image = "",
        onClickBack = {},
        text = "Пример",
        saveState = NavigationIcon({}, {}, false),
        favouriteState = NavigationIcon({}, {}, false)
    )
}

class NavigationIcon(
    val onActivate: () -> Unit,
    val onDeactivate: () -> Unit,
    val isActive: Boolean
)