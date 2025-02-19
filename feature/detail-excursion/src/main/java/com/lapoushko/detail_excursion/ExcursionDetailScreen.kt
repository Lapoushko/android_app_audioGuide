@file:OptIn(ExperimentalMaterial3Api::class)

package com.lapoushko.detail_excursion

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

/**
 * @author Lapoushko
 */
@Composable
fun ExcursionDetailScreen(
    excursion: ExcursionItem
) {
    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            item {
                Column {
                    CustomTopAppBar(
                        image = Uri.EMPTY,
                        onClickBack = { /* TODO */ },
                        text = excursion.name
                    )
                    Box(
                        modifier = Modifier
                            .offset(y = (-25).dp)
                            .zIndex(1f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        ExtendedFloatingActionButton(
                            onClick = { /*TODO*/ },
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
                            onClick = {/*todo*/ },
                            width = 162.dp,
                            height = 238.dp,
                            items = List(5) {
                                (CarouselItem.TitleDescription(
                                    "Название",
                                    "Описание"
                                ))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ExcursionDetailScreenPreview() {
    ExcursionDetailScreen(
        ExcursionItem(
            0,
            "Название",
            "Экскурсия включает такие объекты, как: здание Парламента, прогулка по крыше здания Оперы (построено в 2008 году), крепость Акершуз (возведена более 700 лет назад), набережная Акер-Бригге и посещение городской Ратуши, где ежегодно вручается Нобелевская премия мира.",
            "Категория",
            "Бесплатно",
            "1.2км",
            2.5,
            1
        )
    )
}