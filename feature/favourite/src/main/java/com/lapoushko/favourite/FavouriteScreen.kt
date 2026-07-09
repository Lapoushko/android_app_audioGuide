package com.lapoushko.favourite

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.SelectionScreen
import com.lapoushko.ui.theme.Typography
import com.lapoushko.ui.theme.onSurfaceLight
import com.lapoushko.util.ConnectivityObserver
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

    when(state.internetStatus){
        ConnectivityObserver.Status.AVAILABLE -> {
            SelectionScreen(
                onClickSearch = { viewModel.searchByName(it) },
                onClickDetail = onClickDetail,
                textSearch = "",
                excursions = excursions,
                nameScreen = "Избранное",
            )
        }
        else -> {
            Text(
                "Нет подключения к интернету",
                style = Typography.titleLarge,
                color = onSurfaceLight
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavouriteScreenPreview() {
    FavouriteScreen(
        onClickDetail = {}
    )
}