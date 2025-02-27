package com.lapoushko.selection

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.CustomTopAppBar
import com.lapoushko.ui.ExcursionCard

/**
 * @author Lapoushko
 */

@Composable
fun CategoryScreen(
    handler: CategoryScreenHandler,
    category: String
) {
    val excursions = List(100) {
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

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            CustomTopAppBar(
                image = Uri.EMPTY,
                onClickBack = { handler.onBack() },
                text = category,
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
        }
        items(excursions) { excursion ->
            ExcursionCard(
                onClick = { handler.onToDetail(excursion) },
                excursion = excursion,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun CategoryScreenPreview() {
    CategoryScreen(
        category = "Категория",
        handler = CategoryScreenHandler(onToDetail = {}, onBack = {})
    )
}