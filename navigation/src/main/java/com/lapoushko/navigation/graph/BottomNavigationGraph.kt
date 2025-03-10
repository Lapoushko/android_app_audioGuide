package com.lapoushko.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.lapoushko.detail_excursion.ExcursionDetailScreen
import com.lapoushko.detail_excursion.ExcursionDetailScreenHandler
import com.lapoushko.favourite.FavouriteScreen
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.guide.GuideScreen
import com.lapoushko.guide.GuideScreenHandler
import com.lapoushko.navigation.model.Screen
import com.lapoushko.navigation.model.ScreenBar
import com.lapoushko.profile.ProfileScreen
import com.lapoushko.profile.ProfileScreenHandler
import com.lapoushko.save.SaveExcursionScreen
import com.lapoushko.search.SearchScreen
import com.lapoushko.search.SearchScreenHandler
import com.lapoushko.selection.CategoryScreen
import com.lapoushko.selection.CategoryScreenHandler
import com.lapoushko.setting.SettingProfileScreen
import com.lapoushko.util.ExcursionNavType
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

        composable(route = ScreenBar.Profile.route) { backStackEntry ->
            ProfileScreen(
                ProfileScreenHandler(
                    onSettings = { navController.navigate(Screen.SettingProfile) },
                    onSaves = { navController.navigate(Screen.SavesExcursions) },
                )
            )
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
                handler = ExcursionDetailScreenHandler(
                    onToDetail = { navController.navigate(Screen.ExcursionDetail(it)) },
                    onBack = onBack,
                    onPlayExcursion = { navController.navigate(Screen.AudioGuide(excursion = excursion.excursion)) }
                )
            )
        }

        composable<Screen.AudioGuide>(
            typeMap = mapOf(typeOf<ExcursionItem>() to ExcursionNavType)
        ) { backStackEntry ->
            val excursion = backStackEntry.toRoute<Screen.AudioGuide>().excursion
            GuideScreen(
                excursion = excursion,
                handler = GuideScreenHandler(
                    onBack = onBack
                )
            )
        }
        composable<Screen.SavesExcursions> {
            SaveExcursionScreen(
                onDetail = { navController.navigate(Screen.ExcursionDetail(it)) },
                onBack = onBack
            )
        }
        composable<Screen.SettingProfile> {
            SettingProfileScreen(onBack = onBack)
        }
    }
}