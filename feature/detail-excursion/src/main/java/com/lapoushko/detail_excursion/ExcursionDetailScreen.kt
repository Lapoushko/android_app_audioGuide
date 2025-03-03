@file:OptIn(ExperimentalMaterial3Api::class)

package com.lapoushko.detail_excursion

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.ui.CarouselItem
import com.lapoushko.ui.CustomCarousel
import com.lapoushko.ui.CustomTopAppBar
import com.lapoushko.ui.theme.Typography
import com.lapoushko.ui.theme.onSecondaryContainerLight
import com.lapoushko.ui.theme.primaryLight
import org.koin.androidx.compose.koinViewModel

/**
 * @author Lapoushko
 */
@Composable
fun ExcursionDetailScreen(
    excursion: ExcursionItem,
    viewModel: ExcursionScreenViewModel = koinViewModel(),
    handler: ExcursionScreenHandler
) {
    val excursions = viewModel.state.collectAsState().value
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        CustomTopAppBar(
            image = Uri.EMPTY,
            onClickBack = { handler.onBack() },
            text = excursion.name
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
                width = 162.dp,
                height = 238.dp,
                items = excursions.map {
                    CarouselItem.TitleDescription(
                        it.name,
                        it.description
                    )
                }
            )
        }
    }
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
            "Бесплатно",
            "1.2км",
            2.5,
            1
        ),
        handler = ExcursionScreenHandler(onBack = {}, onToDetail = {}, onPlayExcursion = {})
    )
}