package com.lapoushko.favourite

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
/**
 * @author Lapoushko
 */
@Composable
fun FavouriteScreen(){
    Text("Избранное")
}

@Preview(showBackground = true)
@Composable
private fun FavouriteScreenPreview(){
    FavouriteScreen()
}