package com.zxj.wanandroid.compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zxj.wanandroid.compose.ui.screen.HomeScreen
import com.zxj.wanandroid.compose.ui.screen.search.SearchScreen
import com.zxj.wanandroid.compose.ui.screen.user.LoginScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.HOME,
    ) {
        composable(NavigationRoute.HOME) { HomeScreen(navController) }
        composable(NavigationRoute.SEARCH) { SearchScreen(navController) }
        composable(NavigationRoute.LOGIN) { LoginScreen(navController) }
    }
}

object NavigationRoute {
    val HOME = "/app/home"
    val SEARCH = "/app/search"
    val LOGIN = "/app/login"
}