package com.zxj.wanandroid.compose

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.zxj.wanandroid.compose.ui.browser.addBrowserScreen
import com.zxj.wanandroid.compose.ui.collect.addCollectScreen
import com.zxj.wanandroid.compose.ui.home.addHomeScreen
import com.zxj.wanandroid.compose.ui.rank.addRankScreen
import com.zxj.wanandroid.compose.ui.score.addScoreScreen
import com.zxj.wanandroid.compose.ui.search.addSearchResultScreen
import com.zxj.wanandroid.compose.ui.search.addSearchScreen
import com.zxj.wanandroid.compose.ui.share.addShareScreen
import com.zxj.wanandroid.compose.ui.user.addLoginScreen
import com.zxj.wanandroid.compose.ui.user.addRegisterScreen

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun MainNavigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route,
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
        addScoreScreen(navController)
        addRankScreen(navController)
        addShareScreen(navController)
    }
}

sealed class Screen(val route: String) {
    operator fun invoke(): String = route

    object Home : Screen("/app/home")
    object Search : Screen("/app/search")
    object Register : Screen("/user/register")
    object MineCollect : Screen("/mine/collect")
    object Score : Screen("/user/score")
    object Rank : Screen("/user/rank")
    object Share : Screen("/user/share")
    object Login : Screen("/user/login")

    object SearchDetails : Screen("/app/search/result/{content}") {
        fun search(content: String) = "/app/search/result/${content}"
    }

    object Web : Screen("/browser?webUrl={webUrl}") {
        fun browser(webUrl: String) = "/browser?webUrl=${webUrl}"
    }
}