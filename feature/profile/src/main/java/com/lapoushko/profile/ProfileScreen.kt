package com.lapoushko.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lapoushko.ui.theme.Typography

/**
 * @author Lapoushko
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    handler: ProfileScreenHandler
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Профиль",
                    style = Typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        )
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            TextButton(
                onClick = { handler.onSettings() }
            ) {
                Text(text = "Настройки профиля", style = Typography.bodyLarge)
            }
            TextButton(
                onClick = { handler.onSaves() }
            ) {
                Text(text = "Загруженные экскурсии", style = Typography.bodyLarge)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(ProfileScreenHandler({}, {}, {}))
}