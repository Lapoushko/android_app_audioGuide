@file:OptIn(ExperimentalMaterial3Api::class)

package com.lapoushko.detail_excursion

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.CarouselItem
import com.lapoushko.ui.CustomCarousel
import com.lapoushko.ui.CustomTopAppBar
import com.lapoushko.ui.NavigationIcon
import com.lapoushko.ui.theme.Typography
import com.lapoushko.ui.theme.carouselSizeExcursion
import com.lapoushko.ui.theme.onSecondaryContainerLight
import com.lapoushko.ui.theme.primaryLight
import com.lapoushko.ui.theme.smallCarouselSizeExcursion
import com.lapoushko.util.ConnectivityObserver
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@Composable
fun ExcursionDetailScreen(
    excursion: ExcursionItem,
    viewModel: ExcursionDetailScreenViewModel = koinViewModel(),
    handler: ExcursionDetailScreenHandler,
) {
    val state = viewModel.state
    val excursions = state.interestingExcursion

    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val sizeCardExcursion =
        if (screenWidth < 400.dp) smallCarouselSizeExcursion else carouselSizeExcursion


    val curValue = state.downloadValues.curValue
    val endValue = state.downloadValues.endValue

    val internetStatus = state.internetStatus

    LaunchedEffect(excursion.id) {
        viewModel.setCurrentExcursion(excursion)
        viewModel.loadInterestingExcursions(excursion)
        viewModel.checkIsSaved()
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        CustomTopAppBar(
            image = state.curExcursion.points.firstOrNull()?.image,
            onClickBack = { handler.onBack() },
            text = state.curExcursion.name,
            saveState = NavigationIcon(
                onActive = { viewModel.onSaveButtonClick() },
                onDeactive = { viewModel.onSaveButtonClick() },
                isActive = state.isSaved
            ),
            favouriteState = NavigationIcon({}, {}, false)
        )
        Box(
            modifier = Modifier
                .offset(y = (-25).dp)
                .zIndex(1f)
                .align(Alignment.CenterHorizontally)
        ) {
            ExtendedFloatingActionButton(
                onClick = { handler.onPlayExcursion(state.curExcursion) },
                icon = {
                    Icon(
                        Icons.AutoMirrored.Filled.DirectionsWalk,
                        contentDescription = null
                    )
                },
                text = { Text(text = stringResource(R.string.start_excursion)) },
                shape = RoundedCornerShape(54.dp),
                containerColor = onSecondaryContainerLight,
                contentColor = primaryLight
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = stringResource(R.string.excursion_description),
                style = Typography.headlineSmall
            )
            Text(
                text = state.curExcursion.description,
                style = Typography.bodyLarge,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (internetStatus == ConnectivityObserver.Status.AVAILABLE){
                Text(
                    text = stringResource(R.string.similar_excursions),
                    style = Typography.headlineSmall
                )
                CustomCarousel(
                    onClick = { handler.onToDetail(excursions[it]) },
                    width = sizeCardExcursion.first.dp,
                    height = sizeCardExcursion.second.dp,
                    items = excursions.map {
                        CarouselItem.TitleDescription(
                            it.name,
                            it.description,
                            it.points.firstOrNull()?.image
                        )
                    }
                )
            }
        }
        if (state.isSaveButtonActive) {
            when (state.downloadAlertState) {
                DownloadAlertState.CONFIRM_SAVE -> {
                    SimpleAlertDialog(
                        title = stringResource(R.string.download_excursion),
                        onConfirm = { viewModel.confirmSave(excursion, context) },
                        onDismiss = { viewModel.cancelDialog() }
                    )
                }

                DownloadAlertState.CONFIRM_DELETE -> {
                    SimpleAlertDialog(
                        title = stringResource(R.string.delete_saved_excursion),
                        onConfirm = {
                            viewModel.confirmDelete(
                                state.curExcursion,
                                context,
                                onBackIfFromDao = { if (internetStatus != ConnectivityObserver.Status.AVAILABLE) handler.onBack() }
                            )
                        },
                        onDismiss = { viewModel.cancelDialog() }
                    )
                }

                DownloadAlertState.DOWNLOADING -> {
                    DownloadProgressDialog(
                        curValue = curValue,
                        endValue = endValue,
                        onCancel = { viewModel.cancelDialog() }
                    )
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun SimpleAlertDialog(
    title: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title, style = Typography.titleMedium) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.yes))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.no))
            }
        }
    )
}

@Composable
fun DownloadProgressDialog(
    curValue: Double,
    endValue: Double?,
    onCancel: () -> Unit
) {
    if (endValue == null) {
        AlertDialog(
            onDismissRequest = onCancel,
            title = { Text(stringResource(R.string.loading), style = Typography.titleMedium) },
            text = {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            confirmButton = {
                TextButton(onClick = onCancel) {
                    Text(text = stringResource(R.string.cancel), style = Typography.bodyMedium)
                }
            }
        )
    } else {
        val percent = (curValue / endValue).coerceIn(0.0, 1.0)
        val animatedProgress by animateFloatAsState(
            targetValue = percent.toFloat(),
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
            label = ""
        )

        val isFinished = curValue >= endValue

        AlertDialog(
            onDismissRequest = { if (isFinished) onCancel() },
            title = { Text(stringResource(R.string.download), style = Typography.titleMedium) },
            text = {
                Column {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        progress = { animatedProgress }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${curValue.toInt()} / ${endValue.toInt()}")
                        Text("${(percent * 100).toInt()}%")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = onCancel) {
                    Text(if (isFinished) stringResource(R.string.ready) else stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Preview
@Composable
fun ExcursionDetailScreenPreview() {
    ExcursionDetailScreen(
        ExcursionItem(
            "",
            "Название",
            "Экскурсия включает такие объекты, как: здание Парламента, прогулка по крыше здания Оперы (построено в 2008 году), крепость Акершуз (возведена более 700 лет назад), набережная Акер-Бригге и посещение городской Ратуши, где ежегодно вручается Нобелевская премия мира.",
            listOf("Категория"),
            "1.2км",
            age = "0+",
            2.5,
            1,
            points = emptyList()
        ),
        handler = ExcursionDetailScreenHandler(onBack = {}, onToDetail = {}, onPlayExcursion = {})
    )
}