package com.lapoushko.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.theme.Typography

/**
 * @author Lapoushko
 */
@Composable
fun ExcursionCard(
    onClick: () -> Unit,
    excursion: ExcursionItem,
    modifier: Modifier = Modifier
) {
    val category = excursion.category
    val price = excursion.price
    val distance = excursion.distance
    val description = excursion.description
    val rating = excursion.rating
    val countRating = excursion.countRating

    val painter = rememberAsyncImagePainter(excursion.points.firstOrNull()?.image)
    val state by painter.state.collectAsState()

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            when (state) {
                is AsyncImagePainter.State.Empty,
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator()
                }

                is AsyncImagePainter.State.Success -> {
                    Image(
                        painter = painter,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }

                is AsyncImagePainter.State.Error -> {
                    CircularProgressIndicator()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = excursion.name,
                        style = Typography.bodyLarge,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Text(
                            text = "$rating ($countRating)"
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "${
                            category.joinToString(separator = ", ") { it }
                        } • $price • $distance", style = Typography.bodyMedium
                    )
                    Text(
                        text = description,
                        style = Typography.bodyMedium,
                        maxLines = 2,
                        modifier = Modifier.padding(bottom = 4.dp),
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExcursionCardPreview() {
    ExcursionCard(
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
        ), onClick = {})
}