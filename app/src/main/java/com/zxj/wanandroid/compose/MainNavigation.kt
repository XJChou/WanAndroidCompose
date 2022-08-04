package com.zxj.wanandroid.compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.zxj.wanandroid.compose.ui.browser.addBrowserScreen
import com.zxj.wanandroid.compose.ui.screen.HomeScreen
import com.zxj.wanandroid.compose.ui.search.SearchScreen
import com.zxj.wanandroid.compose.ui.user.LoginScreen
import com.zxj.wanandroid.compose.ui.user.RegisterScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.HOME,
    ) {
        composable(NavigationRoute.HOME) {
            HomeScreen(
                navigation = { navController.navigate(it) },
                onItemClick = { data ->
                    navController.navigate(
                        NavigationRoute.buildBrowserRoute(data.link)
                    )
                }
            )
        }
        composable(NavigationRoute.SEARCH) { SearchScreen(navController) }
        composable(NavigationRoute.LOGIN) { LoginScreen(navController) }
        composable(NavigationRoute.REGISTER) { RegisterScreen(navController) }
        addBrowserScreen(navController)
    }
}

object NavigationRoute {
    val HOME = "/app/home"
    val SEARCH = "/app/search"
    val LOGIN = "/user/login"
    val REGISTER = "/user/register";
    val BROWSER = buildBrowserRoute("{webUrl}")

    fun buildBrowserRoute(webUrl: String): String {
        return "/browser?webUrl=${webUrl}"
    }
}