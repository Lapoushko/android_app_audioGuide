package com.lapoushko.save

import androidx.compose.runtime.Composable
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
        nameScreen = "Загруженнные экскурсии",
    )

//    LazyColumn(
//        modifier = Modifier.fillMaxWidth(),
//        verticalArrangement = Arrangement.spacedBy(20.dp),
//    ) {
//        item {
//            TopAppBar(
//                title = {
//                    Text(
//                        "Загруженные экскурсии",
//                        style = Typography.titleLarge,
//                        color = onSurfaceLight
//                    )
//                },
//                navigationIcon = {
//                    IconButton(
//                        onClick = onBack
//                    ) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = null,
//                            tint = onSurfaceLight
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.Transparent
//                )
//            )
//        }
//        items(excursions) { excursion ->
//            ExcursionCard(
//                onClick = { onDetail(excursion) },
//                excursion = excursion,
//                modifier = Modifier.padding(horizontal = 16.dp)
//            )
//        }
//    }
}

@Preview(showBackground = true)
@Composable
private fun SaveExcursionScreenPreview() {
    SaveExcursionScreen({}, {})
}