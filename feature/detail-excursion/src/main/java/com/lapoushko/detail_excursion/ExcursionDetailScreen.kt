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
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@Composable
fun ExcursionDetailScreen(
    excursion: ExcursionItem,
    viewModel: ExcursionDetailScreenViewModel = koinViewModel(),
    handler: ExcursionDetailScreenHandler
) {
    val state = viewModel.state
    val excursions = state.interestingExcursion

    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val sizeCardExcursion =
        if (screenWidth < 400.dp) smallCarouselSizeExcursion else carouselSizeExcursion

    val isSaveButtonActive = state.isSaveButtonActive
    val isSaved = state.isSaved
    val downloadAlertState = state.downloadAlertState

    val curValue = state.downloadValues.curValue
    val endValue = state.downloadValues.endValue

    LaunchedEffect(excursion.id) {
        viewModel.setCurrentExcursion(excursion)
        viewModel.loadInterestingExcursions(excursion)
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        CustomTopAppBar(
            image = excursion.points.firstOrNull()?.image,
            onClickBack = { handler.onBack() },
            text = excursion.name,
            saveState = NavigationIcon(
                onActive = { viewModel.setIsSavedButtonActive(true) },
                onDeactive = { viewModel.setIsSavedButtonActive(true) },
                isActive = isSaved
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
                onClick = { handler.onPlayExcursion(excursion) },
                icon = {
                    Icon(
                        Icons.AutoMirrored.Filled.DirectionsWalk,
                        contentDescription = null
                    )
                },
                text = { Text(text = "Запустить маршрут") },
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
                text = "Описание",
                style = Typography.headlineSmall
            )
            Text(
                text = excursion.description,
                style = Typography.bodyLarge,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Похожее",
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
        if (isSaveButtonActive) {
            when (downloadAlertState) {
                DownloadAlertState.DELETING ->
                    SaveAlertDialog(
                        content = { ContentText("Вы хотите удалить экскурсию?") },
                        onAgree = {
                            viewModel.deleteExcursion(excursion, context)
                            viewModel.setIsSavedButtonActive(false)
                        },
                        onDisagree = { viewModel.setIsSavedButtonActive(false) }
                    )

                DownloadAlertState.SAVING ->
                    SaveAlertDialog(
                        content = { ContentText("Вы хотите скачать экскурсию?") },
                        onAgree = {
                            viewModel.setDownloadAlertState(DownloadAlertState.DOWNLOADING)
                        },
                        onDisagree = { viewModel.setIsSavedButtonActive(false) }
                    )

                DownloadAlertState.DOWNLOADING -> {
                    DownloadAlertDialog(
                        content = { ContentDownload(curValue = curValue, endValue = endValue) },
                        onAgree = {
                            viewModel.saveExcursion(excursion, context)
                            viewModel.setIsSavedButtonActive(false)
                            viewModel.clearTimer()
                        },
                        onDisagree = {
                            viewModel.setDownloadAlertState(DownloadAlertState.SAVING)
                            viewModel.setIsSavedButtonActive(false)
                            viewModel.clearTimer()
                        },
                        title = "Скачивание",
                        textDownloaded = "Готово",
                        textNotDownloaded = "Отмен а",
                        isDownloaded = curValue >= endValue
                    )
                }

                DownloadAlertState.EMPTY -> {}
            }
        }
    }
}

@Composable
private fun DownloadAlertDialog(
    title: String = "Подтверждение",
    content: @Composable () -> Unit,
    textDownloaded: String = "Готово",
    textNotDownloaded: String = "Отмена",
    isDownloaded: Boolean,
    onAgree: () -> Unit,
    onDisagree: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = title, style = Typography.titleMedium)
        },
        text = {
            content()
        },
        onDismissRequest = if (isDownloaded) onAgree else onDisagree,
        confirmButton = {
            when (isDownloaded) {
                true ->
                    TextButton(onClick = onAgree) {
                        Text(text = textDownloaded, style = Typography.bodyMedium)
                    }

                false ->
                    TextButton(onClick = onDisagree) {
                        Text(text = textNotDownloaded, style = Typography.bodyMedium)
                    }
            }
        },
    )
}

@Composable
private fun SaveAlertDialog(
    title: String = "Подтверждение",
    content: @Composable () -> Unit,
    textAgree: String = "Да",
    textDisagree: String = "Нет",
    onAgree: () -> Unit,
    onDisagree: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = title, style = Typography.titleMedium)
        },
        text = {
            content()
        },
        onDismissRequest = onDisagree,
        confirmButton = {
            TextButton(onClick = onAgree) {
                Text(text = textAgree, style = Typography.bodyMedium)
            }
        },
        dismissButton = {
            TextButton(onClick = onDisagree) {
                Text(text = textDisagree, style = Typography.bodyMedium)
            }
        }
    )
}

@Composable
private fun ContentText(text: String) {
    Text(text = text, style = Typography.titleMedium)
}

@Composable
private fun ContentDownload(
    curValue: Float,
    endValue: Float,
) {
    val percent = curValue / endValue
    val animatedProgress by animateFloatAsState(
        targetValue = percent,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = ""
    )

    Column {
        Column {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = { animatedProgress },
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "$curValue", style = Typography.labelMedium)
                Text(text = "${(percent * 100).toInt()} %", style = Typography.labelMedium)
                Text(text = "$endValue", style = Typography.labelMedium)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun DownloadAlertPreview() {
    SaveAlertDialog(
        content = { ContentDownload(1f, 10f) },
//        content = { ContentText("Вы хотите скачать экскурсию?")},
        textAgree = "Да",
        textDisagree = "Нет",
        onAgree = {},
        onDisagree = {}
    )
}

@Preview
@Composable
fun ExcursionDetailScreenPreview() {
    ExcursionDetailScreen(
        ExcursionItem(
            "",
            "Название",
            "Экскурсия включает такие объекты, как: здание Парламента, прогулка по крыше здания Оперы (построено в 2008 году), крепость Акершуз (возведена более 700 лет назад), набережная Акер-Бригге и посещение городской Ратуши, где ежегодно вручается Нобелевская премия мира.",
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