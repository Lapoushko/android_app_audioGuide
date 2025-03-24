package com.lapoushko.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lapoushko.ui.CarouselItem
import com.lapoushko.ui.CustomCarousel
import com.lapoushko.ui.CustomSearchBar
import com.lapoushko.ui.ExcursionCard
import com.lapoushko.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@Composable
fun SearchScreen(
    handler: SearchScreenHandler,
    viewModel: SearchScreenViewModel = koinViewModel()
) {
    val state = viewModel.state

    val specialExcursions = state.specialExcursions
    val interesting = state.interesting
    val categories = state.categories

    val isCategoriesLoaded = categories.isNotEmpty()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            CustomSearchBar(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .heightIn(max = 64.dp)
            )
        }

        // Популярное
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextTitle(
                    text = "Популярное",
                    onClick = { viewModel.setIsNewExcursions(false) },
                    isActive = !state.isNew
                )
                TextTitle(
                    text = "Новое",
                    onClick = { viewModel.setIsNewExcursions(true) },
                    isActive = state.isNew
                )
            }
            CustomCarousel(
                onClick = { handler.onToDetail(specialExcursions[it]) },
                width = 162.dp,
                height = 238.dp,
                items = specialExcursions.map {
                    CarouselItem.TitleDescription(
                        title = it.name,
                        description = it.description,
                        image = it.points.firstOrNull()?.image
                    )
                }
            )
        }

        item {
            TextTitle("Интересное")
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                interesting.forEach { excursion ->
                    ExcursionCard(
                        onClick = { handler.onToDetail(excursion) },
                        excursion = excursion
                    )
                }
            }
        }

        item {
            TextTitle("Категории")
            if (isCategoriesLoaded) {
                CustomCarousel(
                    onClick = { handler.onToCategory(categories[it].category) },
                    width = 348.dp,
                    height = 214.dp,
                    items = categories
                )
            } else {
                CircularProgressIndicator()
            }
        }
    }
}


@Composable
private fun TextTitle(text: String, onClick: () -> Unit = {}, isActive: Boolean = true) {
    Text(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .clickable { onClick() },
        text = text,
        color = if (isActive) Color.Black else Color.Gray,
        style = Typography.headlineSmall
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(handler = SearchScreenHandler(onToCategory = {}, onToDetail = {}))
}
