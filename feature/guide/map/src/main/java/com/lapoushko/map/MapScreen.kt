package com.lapoushko.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.maps.android.compose.GoogleMap

/**
 * @author Lapoushko
 */
@Composable
fun MapScreen() {
    var isMapLoaded by remember { mutableStateOf(false) }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        onMapLoaded = { isMapLoaded = true }
    )
}

@Preview(showBackground = true)
@Composable
private fun MapScreenPreview() {
    MapScreen()
}