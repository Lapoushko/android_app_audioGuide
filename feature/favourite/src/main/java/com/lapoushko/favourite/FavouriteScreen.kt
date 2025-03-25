package com.lapoushko.favourite

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.SelectionScreen
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@Composable
fun FavouriteScreen(
    onClickDetail: (ExcursionItem) -> Unit,
    viewModel: FavouriteScreenViewModel = koinViewModel()
) {
    val state = viewModel.state
    val excursions = state.excursions

    SelectionScreen(
        onClickSearch = { viewModel.searchByName(it) },
        onClickDetail = onClickDetail,
        textSearch = "",
        excursions = excursions,
        nameScreen = "Избранное",
    )
}

@Preview(showBackground = true)
@Composable
private fun FavouriteScreenPreview() {
    FavouriteScreen(
        onClickDetail = {}
    )
}