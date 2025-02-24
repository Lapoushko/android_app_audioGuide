package com.lapoushko.navigation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * @author Lapoushko
 */
sealed class ScreenBar(
    val route: String,
    val title: String,
    val setIcon: ImageVector,
    val unsetIcon: ImageVector? = null
) {

    data object Search : ScreenBar(
        route = SEARCH_ROUTE,
        title = SEARCH_TITLE,
        setIcon = SEARCH_SET_ICON,
        unsetIcon = SEARCH_UNSET_ICON
    )


    data object Favourite : ScreenBar(
        route = FAVOURITE_ROUTE,
        title = FAVOURITE_TITLE,
        setIcon = FAVOURITE_SET_ICON,
        unsetIcon = FAVOURITE_UNSET_ICON
    )


    data object Profile : ScreenBar(
        route = PROFILE_ROUTE,
        title = PROFILE_TITLE,
        setIcon = PROFILE_SET_ICON,
        unsetIcon = PROFILE_UNSET_ICON
    )

    companion object {
        private const val SEARCH_ROUTE = "SEARCH"
        private const val SEARCH_TITLE = "Поиск"
        private val SEARCH_SET_ICON = Icons.Filled.Search
        private val SEARCH_UNSET_ICON = Icons.Outlined.Search

        private const val FAVOURITE_ROUTE = "favourite"
        private const val FAVOURITE_TITLE = "Избранное"
        private val FAVOURITE_SET_ICON = Icons.Filled.Bookmark
        private val FAVOURITE_UNSET_ICON = Icons.Outlined.Bookmark

        private const val PROFILE_ROUTE = "profile"
        private const val PROFILE_TITLE = "Профиль"
        private val PROFILE_SET_ICON = Icons.Filled.AccountCircle
        private val PROFILE_UNSET_ICON = Icons.Outlined.AccountCircle
    }
}