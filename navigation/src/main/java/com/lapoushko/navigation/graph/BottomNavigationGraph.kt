package com.lapoushko.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.lapoushko.detail_excursion.ExcursionDetailScreen
import com.lapoushko.detail_excursion.ExcursionScreenHandler
import com.lapoushko.favourite.FavouriteScreen
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.navigation.model.Screen
import com.lapoushko.navigation.model.ScreenBar
import com.lapoushko.navigation.util.ExcursionNavType
import com.lapoushko.profile.ProfileScreen
import com.lapoushko.search.SearchScreen
import com.lapoushko.search.SearchScreenHandler
import com.lapoushko.selection.CategoryScreen
import com.lapoushko.selection.CategoryScreenHandler
import kotlin.reflect.typeOf

/**
 * @author Lapoushko
 */

@Composable
fun BottomNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ScreenBar.Search.route
    ) {
        val onBack: () -> Unit = {
            navController.popBackStack()
        }

        composable(route = ScreenBar.Search.route) {
            SearchScreen(
                SearchScreenHandler(
                    onToCategory = {
                        navController.navigate(
                            Screen.Category(it)
                        )
                    },
                    onToDetail = {
                        navController.navigate(
                            Screen.ExcursionDetail(it)
                        )
                    }
                )
            )
        }
        composable(route = ScreenBar.Favourite.route) {
            FavouriteScreen()
        }

        composable(route = ScreenBar.Profile.route) {
            ProfileScreen()
        }
        composable<Screen.Category> {
            CategoryScreen(
                category = "Категория",
                handler = CategoryScreenHandler(
                    onBack = onBack,
                    onToDetail = { navController.navigate(Screen.ExcursionDetail(it)) }
                )
//                handler = SelectionScreenHandler(
//                    onToDetail = { navController.navigate(Screen.ExcursionDetail(it)) },
//                    onToBack = { navController.popBackStack() }
//                ),
            )
        }
        composable<Screen.ExcursionDetail>(
            typeMap = mapOf(typeOf<ExcursionItem>() to ExcursionNavType)
        ) { backStackEntry ->
            val excursion = backStackEntry.toRoute<Screen.ExcursionDetail>()
            ExcursionDetailScreen(
                excursion = excursion.excursion,
                handler = ExcursionScreenHandler(
                    onToDetail = { navController.navigate(Screen.ExcursionDetail(it)) },
                    onBack = onBack
                )
            )
        }
    }
}