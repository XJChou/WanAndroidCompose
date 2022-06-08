package com.zxj.wanandroid.compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.zxj.wanandroid.compose.ui.page.Home
import com.zxj.wanandroid.compose.ui.page.search.Search

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.HOME,
    ) {
//        composable(NavigationRoute.HOME) { Home(navController) }
//        composable(NavigationRoute.SEARCH) { Search(navController) }
    }
}

object NavigationRoute{
    val HOME = "/app/home"
    val SEARCH = "/app/search"
}