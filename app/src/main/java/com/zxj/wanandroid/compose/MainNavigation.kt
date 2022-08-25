package com.zxj.wanandroid.compose

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.zxj.wanandroid.compose.ui.browser.addBrowserScreen
import com.zxj.wanandroid.compose.ui.collect.addCollectScreen
import com.zxj.wanandroid.compose.ui.home.addHomeScreen
import com.zxj.wanandroid.compose.ui.search.addSearchResultScreen
import com.zxj.wanandroid.compose.ui.search.addSearchScreen
import com.zxj.wanandroid.compose.ui.user.addLoginScreen
import com.zxj.wanandroid.compose.ui.user.addRegisterScreen

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun MainNavigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = NavigationRoute.HOME,
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
    ) {
        addHomeScreen(navController)
        addSearchScreen(navController)
        addSearchResultScreen(navController)
        addLoginScreen(navController)
        addRegisterScreen(navController)
        addBrowserScreen(navController)
        addCollectScreen(navController)
    }
}

object NavigationRoute {
    val HOME = "/app/home"
    val SEARCH = "/app/search"
    val SEARCH_RESULT = buildSearchResultRoute("{content}")
    val LOGIN = "/user/login"
    val REGISTER = "/user/register";
    val BROWSER = buildBrowserRoute("{webUrl}")
    val COLLECT = "/user/collect"

    fun buildSearchResultRoute(content: String): String {
        return "/app/search/result?content=${content}"
    }

    fun buildBrowserRoute(webUrl: String): String {
        return "/browser?webUrl=${webUrl}"
    }
}