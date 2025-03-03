package com.lapoushko.guide

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lapoushko.audio.AudioScreen
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.map.MapScreen
import com.lapoushko.ui.CustomOutlinedButton
import kotlinx.coroutines.launch

@Composable
fun GuideScreen(
    excursion: ExcursionItem,
    handler: GuideScreenHandler
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TopMenu(
            handler = handler,
            currentPage = pagerState.currentPage,
            onPageSelected = { page ->
                scope.launch {
                    pagerState.animateScrollToPage(page)
                }
            }
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> AudioScreen(excursion)
                1 -> MapScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopMenu(
    handler: GuideScreenHandler,
    currentPage: Int,
    onPageSelected: (Int) -> Unit
) {
    Column {
        Column {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { handler.onBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomOutlinedButton(
                    text = "Описание",
                    onClick = { onPageSelected(0) },
                    imageVector = Icons.Filled.MusicNote,
                    isActive = currentPage == 0
                )
                Spacer(modifier = Modifier.width(8.dp))
                CustomOutlinedButton(
                    text = "Карта",
                    onClick = { onPageSelected(1) },
                    imageVector = Icons.Filled.Map,
                    isActive = currentPage == 1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabsScreenPreview() {
    GuideScreen(
        excursion = ExcursionItem(
            "",
            "Название",
            "Описание",
            listOf("Категория"),
            "Бесплатно",
            "1.2км",
            2.5,
            1
        ),
        GuideScreenHandler(onBack = {}),
    )
}
