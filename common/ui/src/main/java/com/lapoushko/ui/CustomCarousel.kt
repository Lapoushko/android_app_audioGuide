@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.lapoushko.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.lapoushko.ui.theme.Typography

/**
 * @author Lapoushko
 */
@Composable
fun CustomCarousel(
    onClick: (Int) -> Unit,
    width: Dp,
    height: Dp,
    items: List<CarouselItem>
) {
    HorizontalUncontainedCarousel(
        state = rememberCarouselState {
            items.count()
        },
        itemWidth = width,
        itemSpacing = 12.dp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) { index ->
        val item = items[index]
        CustomCard(
            item = item,
            onClick = { onClick(index) },
            modifier = Modifier
                .height(height)
                .maskClip(RoundedCornerShape(18.dp))
        )
    }
}

@Composable
fun CustomCard(
    item: CarouselItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val image = (item as? CarouselItem.WithImage)?.image ?: R.drawable.example

    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, bottom = 16.dp)
                    .align(Alignment.BottomStart),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                when (item) {
                    is CarouselItem.TitleDescription -> {
                        Text(
                            text = item.title,
                            style = Typography.bodyLarge,
                            color = Color.White
                        )
                        Text(
                            text = item.description,
                            style = Typography.labelSmall,
                            color = Color.White
                        )
                    }

                    is CarouselItem.Category -> {
                        Text(
                            text = item.category,
                            style = Typography.bodyLarge,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

sealed class CarouselItem() {
    interface WithImage {
        val image: String?
    }

    data class TitleDescription(
        val title: String,
        val description: String,
        override val image: String? = null
    ) : CarouselItem(), WithImage

    data class Category(val category: String, override val image: String? = null) : CarouselItem(),
        WithImage
}

@Preview
@Composable
private fun CustomCarouselPreview1() {
    CustomCarousel(
        onClick = {},
        width = 162.dp,
        height = 238.dp,
        items = List(5) { CarouselItem.TitleDescription("Название", "Описание") },
    )
}

@Preview
@Composable
private fun CustomCarouselPreview2() {
    CustomCarousel(
        onClick = {},
        width = 348.dp,
        height = 214.dp,
        items = List(5) { CarouselItem.Category("Категория") },
    )
}