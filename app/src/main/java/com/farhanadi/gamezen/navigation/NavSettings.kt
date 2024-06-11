package com.farhanadi.gamezen.navigation

sealed class NavSettings(val route: String)
    {
    object Home : NavSettings("Home")
    object Favorite : NavSettings("Favorite")
    object Profile : NavSettings("Profile")
    object Detail : NavSettings("Home/{gameId}") {
        fun createRoute(fishId: Int) = "Home/$fishId"
    }
}

