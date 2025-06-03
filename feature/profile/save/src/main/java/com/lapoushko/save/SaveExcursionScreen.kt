package com.lapoushko.save

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.SelectionScreen
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@Composable
fun SaveExcursionScreen(
    onDetail: (ExcursionItem) -> Unit,
    onBack: () -> Unit,
    viewModel: SaveExcursionScreenViewModel = koinViewModel()
) {
    val state = viewModel.state
    val excursions = state.excursions

    SelectionScreen(
        onClickDetail = onDetail,
        onClickBack = onBack,
        textSearch = "",
        excursions = excursions,
        onClickSearch = { viewModel.searchByName(it) },
        nameScreen = stringResource(R.string.downloaded_excursions),
    )
}

@Preview(showBackground = true)
@Composable
private fun SaveExcursionScreenPreview() {
    SaveExcursionScreen({}, {})
}