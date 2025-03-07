package com.lapoushko.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    val popular = state.popular
    val interesting = state.interesting
    val categories = state.categories

    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            CustomSearchBar(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .heightIn(max = 64.dp)
            )
        }

        item {
            Column {
                TextTitle("Популярное")
                CustomCarousel(
                    onClick = { handler.onToDetail(popular[it]) },
                    width = 162.dp,
                    height = 238.dp,
                    items = popular.map {
                        CarouselItem.TitleDescription(
                            title = it.name,
                            description = it.description,
                            image = it.points.firstOrNull()?.image
                        )
                    }
                )
            }
        }
        item {
            Column {
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
        }
        items(categories) { category ->
            Column {
                TextTitle("Категории")
                CustomCarousel(
                    onClick = { handler.onToCategory(category.category) },
                    width = 348.dp,
                    height = 214.dp,
                    items = categories
                )
            }
        }
    }
}

@Composable
private fun TextTitle(text: String) {
    Text(
        text = text,
        style = Typography.headlineSmall,
        modifier = Modifier.padding(bottom = 20.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(handler = SearchScreenHandler(onToCategory = {}, onToDetail = {}))
}
