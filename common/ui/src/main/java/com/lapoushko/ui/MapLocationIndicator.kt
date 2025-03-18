package com.lapoushko.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.lapoushko.ui.theme.Typography

/**
 * @author Lapoushko
 */
@Composable
fun MapLocationIndicator(image: String, rating: Double){
    Box(
        modifier = Modifier.size(100.dp).clip(CircleShape)
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = image,
                contentScale = ContentScale.Crop,
                contentDescription = "indicator"
            )
            Text(text = rating.toString(), style = Typography.labelLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MapLocationIndicatorPreview(){
    MapLocationIndicator("",0.0)
}