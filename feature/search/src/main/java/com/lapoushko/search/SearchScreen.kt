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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lapoushko.feature.model.CategoryItem
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.CarouselItem
import com.lapoushko.ui.CustomCarousel
import com.lapoushko.ui.CustomSearchBar
import com.lapoushko.ui.ExcursionCard
import com.lapoushko.ui.SelectionScreen
import com.lapoushko.ui.theme.Typography
import com.lapoushko.ui.theme.carouselSizeCategory
import com.lapoushko.ui.theme.carouselSizeExcursion
import com.lapoushko.ui.theme.smallCarouselSizeCategory
import com.lapoushko.ui.theme.smallCarouselSizeExcursion
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@Composable
fun SearchScreen(
    handler: SearchScreenHandler,
    viewModel: SearchScreenViewModel = koinViewModel()
) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp

    val sizeCardExcursion =
        if (screenWidth < 400.dp) smallCarouselSizeExcursion else carouselSizeExcursion
    val sizeCardCategory =
        if (screenWidth < 400.dp) smallCarouselSizeCategory else carouselSizeCategory

    val isNew = remember { mutableStateOf(false) }
    val state = viewModel.state
    val interesting = state.interesting
    val categories = state.categories

    val isCategoriesLoaded = categories.isNotEmpty()

    val query = remember { mutableStateOf("") }

    if (state.isSearch) {
        SelectionScreen(
            onClickBack = { viewModel.updateIsSearch(false) },
            textSearch = query.value,
            onClickDetail = { handler.onToDetail(it) },
            onClickSearch = {
                viewModel.searchByName(it)
                viewModel.updateIsSearch(true)
            },
            excursions = state.allInteresting,
            nameScreen = "Поиск"
        )
    } else {
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
                        .heightIn(max = 64.dp),
                    queryReturn = { query.value = it },
                    onBack = { viewModel.updateIsSearch(false) },
                    onClick = {
                        viewModel.updateIsSearch(true)
                        viewModel.searchByName(query.value)
                    }
                )
            }

            // Популярное
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextTitle(
                        text = "Популярное",
                        onClick = {
                            isNew.value = false
                        },
                        isActive = !isNew.value
                    )
                    TextTitle(
                        text = "Новое",
                        onClick = {
                            isNew.value = true
                        },
                        isActive = isNew.value
                    )
                }
                //Нужно вызывать разные, поскольку динамически карусель не меняет размер
                if (isNew.value) {
                    Carousel(
                        onClick = { handler.onToDetail(viewModel.state.news[it]) },
                        items = viewModel.state.news,
                        width = sizeCardExcursion.first.dp,
                        height = sizeCardExcursion.second.dp
                    )
                } else {
                    Carousel(
                        onClick = { handler.onToDetail(viewModel.state.populars[it]) },
                        items = viewModel.state.populars,
                        width = sizeCardExcursion.first.dp,
                        height = sizeCardExcursion.second.dp
                    )
                }
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
                        onClick = {
                            handler.onToCategory(
                                CategoryItem(
                                    name = categories[it].category,
                                    image = categories[it].image
                                )
                            )
                        },
                        width = sizeCardCategory.first.dp,
                        height = sizeCardCategory.second.dp,
                        items = categories
                    )
                } else {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun Carousel(
    onClick: (Int) -> Unit,
    items: List<ExcursionItem>,
    width: Dp,
    height: Dp
) {
    CustomCarousel(
        onClick = { index ->
            onClick(index)
        },
        width = width,
        height = height,
        items = items.map {
            CarouselItem.TitleDescription(
                title = it.name,
                description = it.description,
                image = it.points.firstOrNull()?.image
            )
        }
    )
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
