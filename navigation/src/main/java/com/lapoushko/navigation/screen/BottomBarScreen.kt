package com.lapoushko.navigation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lapoushko.navigation.graph.BottomNavigationGraph
import com.lapoushko.navigation.model.ScreenBar
import com.lapoushko.ui.theme.Typography
import com.lapoushko.ui.theme.onSurfaceLight

/**
 * @author Lapoushko
 */

@Composable
fun BottomBarScreen(
    navController: NavHostController,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screens = listOf(
        ScreenBar.Search.route,
        ScreenBar.Favourite.route,
        ScreenBar.Profile.route
    )

    val showBottomBar = currentDestination?.route in screens

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    navController = navController,
                )
            }
        }

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            BottomNavigationGraph(navController = navController)
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
) {
    val items = listOf(
        ScreenBar.Search,
        ScreenBar.Favourite,
        ScreenBar.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination

//    val countQueries by viewModel?.countQueries?.collectAsState(initial = 0)
//        ?: remember { mutableIntStateOf(0) }


    NavigationBar {
        items.forEach { screen ->
//            val badges by derivedStateOf { if (screen == ScreenBar.Favourite) countQueries else 0 }
            AddItem(
                screen = screen,
                destination = destination,
                navController = navController,
                badges = 0
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomBarScreen(
        navController = rememberNavController(),
    )
}

@Composable
private fun RowScope.AddItem(
    screen: ScreenBar,
    destination: NavDestination?,
    navController: NavHostController,
    badges: Int = 0
) {
    NavigationBarItem(
        selected = destination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BadgedBox(
                    badge = {
                        if (badges > 0) {
                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ) {
                                Text(text = "$badges", style = Typography.labelMedium)
                            }
                        }
                    }
                ) {
                    if (destination?.hierarchy?.any { it.route == screen.route } == true) {
                        CustomNavIcon(
                            imageVector = screen.setIcon,
                            title = screen.title,
                        )
                    } else {
                        CustomNavIcon(
                            imageVector = screen.unsetIcon ?: screen.setIcon,
                            title = screen.title,
                        )
                    }
                }
            }
        },
        label = {
            Text(
                text = screen.title,
                style = Typography.labelMedium,
                color = onSurfaceLight
            )
        }
    )
}

@Composable
private fun CustomNavIcon(
    imageVector: ImageVector,
    title: String,
) {
    Icon(
        modifier = Modifier.size(24.dp),
        imageVector = imageVector,
        contentDescription = title,
    )
}