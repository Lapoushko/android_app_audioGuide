package com.lapoushko.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.theme.Typography
import com.lapoushko.ui.theme.onSurfaceLight

/**
 * @author Lapoushko
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionScreen(
    onClickBack: (() -> Unit)? = null,
    textSearch: String = "",
    onClickDetail: (ExcursionItem) -> Unit,
    onClickSearch: (String) -> Unit,
    excursions: List<ExcursionItem>,
    nameScreen: String
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item { 
            CustomSearchBar(
                onClick = {onClickSearch(it)},
                text = textSearch,
                onBack = onClickBack,
                modifier = Modifier
                    .heightIn(max = 64.dp)
            )
            TopAppBar(
                title = {
                    Text(
                        nameScreen,
                        style = Typography.titleLarge,
                        color = onSurfaceLight
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
        items(excursions) { excursion ->
            ExcursionCard(
                onClick = { onClickDetail(excursion) },
                excursion = excursion,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionScreenPreview() {
    SelectionScreen(
        onClickBack = {},
        onClickDetail = {},
        excursions = List(5) { ExcursionItem() },
        textSearch = "",
        onClickSearch = {},
        nameScreen = "Название"
    )
}