package com.farhanadi.gamezen

import AboutActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.farhanadi.gamezen.navigation.NavItem
import com.farhanadi.gamezen.navigation.NavSettings
import com.farhanadi.gamezen.ui.activity.detail.DetailActivity
import com.farhanadi.gamezen.ui.activity.favorite.FavoriteActivity
import com.farhanadi.gamezen.ui.activity.home.HomeActivity
import com.farhanadi.gamezen.ui.theme.DarkPurple

@Composable
fun NavUtils (
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != NavSettings.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavSettings.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavSettings.Home.route) {
                HomeActivity(
                    navigateToDetail = { gameId ->
                        navController.navigate(NavSettings.Detail.createRoute(gameId))
                    }
                )
            }
            composable(NavSettings.Favorite.route) {
                FavoriteActivity(
                    navigateToDetail = { gameId ->
                        navController.navigate(NavSettings.Detail.createRoute(gameId))
                    }
                )
            }
            composable(NavSettings.Profile.route) {
                AboutActivity()
            }
            composable(
                route = NavSettings.Detail.route,
                arguments = listOf(
                    navArgument("gameId") { type = NavType.IntType }
                )
            ) {
                val id = it.arguments?.getInt("gameId") ?: -1
                DetailActivity(
                    gameId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }

        }
    }
}


@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
            .height(70.dp),
        elevation = 8.dp,
        backgroundColor = colorResource(id = R.color.DarkPurple)

    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavItem(
                title = stringResource(R.string.home_menu),
                iconDrawable = R.drawable.home_icon,
                screen = NavSettings.Home
            ),
            NavItem(
                title = stringResource(R.string.favorite_menu),
                iconDrawable = R.drawable.favorite_icon,
                screen = NavSettings.Favorite
            ),
            NavItem(
                title = stringResource(R.string.profile_menu),
                iconDrawable = R.drawable.profile_icon,
                screen = NavSettings.Profile
            ),
        )
        BottomNavigation (
            modifier = modifier
                .height(70.dp),
            backgroundColor = colorResource(id = R.color.DarkPurple)

        ){
            navigationItems.map { item ->
                val painter = painterResource(id = item.iconDrawable)
                BottomNavigationItem(
                    icon = {
                        Image(
                            painter = painter,
                            contentDescription = item.title,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White
                        )
                    },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

