package com.lapoushko.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.CarouselItem
import com.lapoushko.ui.CustomCarousel
import com.lapoushko.ui.CustomSearchBar
import com.lapoushko.ui.ExcursionCard
import com.lapoushko.ui.theme.Typography

/**
 * @author Lapoushko
 */
@Composable
fun SearchScreen() {
    val interesting =
        List(5) { CarouselItem.TitleDescription(title = "Название", description = "Описание") }
    val categories = List(5) { CarouselItem.Category("Категория") }

    val excursions = List(5) {
        ExcursionItem(
            0,
            "Название",
            "Описание",
            "Категория",
            "Бесплатно",
            "1.2км",
            2.5,
            1
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            CustomSearchBar(modifier = Modifier.padding(vertical = 20.dp))
            LazyColumn {
                item {
                    Column(modifier = Modifier.padding(vertical = 20.dp)) {
                        TextTitle("Популярное")
                        CustomCarousel(
                            onClick = { /* TODO */ },
                            width = 162.dp,
                            height = 238.dp,
                            items = interesting
                        )
                    }
                    Column(modifier = Modifier.padding(vertical = 20.dp)) {
                        TextTitle("Интересное")
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            excursions.forEach { excursion ->
                                ExcursionCard(onClick = {}, excursion = excursion)
                            }
                        }
                    }
                    Column(modifier = Modifier.padding(vertical = 20.dp)) {
                        TextTitle("Категории")
                        Column {
                            CustomCarousel(
                                onClick = { /* TODO */ },
                                width = 348.dp,
                                height = 214.dp,
                                items = categories
                            )
                        }
                    }
                }
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

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen()
}