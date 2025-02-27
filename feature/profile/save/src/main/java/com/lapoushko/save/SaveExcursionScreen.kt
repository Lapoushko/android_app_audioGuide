package com.lapoushko.save

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.ExcursionCard
import com.lapoushko.ui.theme.Typography
import com.lapoushko.ui.theme.onSurfaceLight

/**
 * @author Lapoushko
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveExcursionScreen(
    onDetail: (ExcursionItem) -> Unit,
    onBack: () -> Unit
) {
    val excursions = List(5) { index ->
        ExcursionItem(
            id = index.toLong(),
            name = "Название $index",
            description = "Описание $index",
            category = "Категория",
            price = "Цена $index",
            distance = "Расстояние $index",
            rating = index.toDouble(),
            countRating = index.toLong(),
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            TopAppBar(
                title = {
                    Text(
                        "Загруженные экскурсии",
                        style = Typography.titleLarge,
                        color = onSurfaceLight
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = onSurfaceLight
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
        items(excursions) { excursion ->
            ExcursionCard(
                onClick = { onDetail(excursion) },
                excursion = excursion,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SaveExcursionScreenPreview() {
    SaveExcursionScreen({}, {})
}