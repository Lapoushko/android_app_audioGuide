package com.lapoushko.map

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * @author Lapoushko
 */
@Composable
fun MapScreen() {
    Text("Здесь будут карты")
}

@Preview(showBackground = true)
@Composable
private fun MapScreenPreview() {
    MapScreen()
}