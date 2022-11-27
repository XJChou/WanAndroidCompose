package com.zxj.wanandroid.compose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.zxj.article.navigation.homeRoute
import com.zxj.article.navigation.homeScreen
import com.zxj.collect.navigation.collectScreen
import com.zxj.collect.navigation.navigateToCollect
import com.zxj.rank.navigation.navigateToRank
import com.zxj.rank.navigation.rankScreen
import com.zxj.score.navigation.navigateToScore
import com.zxj.score.navigation.scoreScreen
import com.zxj.search.navigation.navigateToSearch
import com.zxj.search.navigation.navigationToSearchDetail
import com.zxj.search.navigation.searchGraph
import com.zxj.share.navigation.navigateToShare
import com.zxj.share.navigation.shareScreen
import com.zxj.user.navigation.accountGraph
import com.zxj.user.navigation.navigateToLogin
import com.zxj.user.navigation.navigateToRegister
import com.zxj.web.navigation.browserScreen
import com.zxj.web.navigation.navigateToBrowser


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WanAndroidNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = homeRoute,
        modifier = modifier,
//        exitTransition = {
//            slideOutOfContainer(
//                AnimatedContentScope.SlideDirection.Left,
//                animationSpec = tween(500)
//            )
//        },
//        enterTransition = {
//            slideIntoContainer(
//                AnimatedContentScope.SlideDirection.Left,
//                animationSpec = tween(500)
//            )
//        },
//        popExitTransition = {
//            slideOutOfContainer(
//                AnimatedContentScope.SlideDirection.Right,
//                animationSpec = tween(500)
//            )
//        },
//        popEnterTransition = {
//            slideIntoContainer(
//                AnimatedContentScope.SlideDirection.Right,
//                animationSpec = tween(500)
//            )
//        }
    ) {
        // 主页
        this.homeScreen(
            navigateToSearch = navController::navigateToSearch,
            navigateToCollect = navController::navigateToCollect,
            navigateToLogin = navController::navigateToLogin,
            navigateToScore = navController::navigateToScore,
            navigateToRank = navController::navigateToRank,
            navigateToShare = navController::navigateToShare,
            navigateToSetting = {},
            navigateToTODO = {},
            navigateToBrowser = navController::navigateToBrowser
        )

        // 收藏
        this.collectScreen(
            onBack = navController::popBackStack,
            onBrowser = navController::navigateToBrowser
        )

        // 排行榜
        this.rankScreen(
            onBack = navController::popBackStack
        )

        // 账号[注册登录]
        this.accountGraph(
            onBack = navController::popBackStack,
            navigateToRegister = navController::navigateToRegister,
            navigateToLogin = navController::navigateToLogin
        )

        // 积分显示
        this.scoreScreen(
            onBack = navController::popBackStack,
            onBrowser = navController::navigateToBrowser
        )

        // 搜索
        this.searchGraph(
            onBack = navController::popBackStack,
            onBrowser = navController::navigateToBrowser,
            onSearch = navController::navigationToSearchDetail
        )

        // 添加分享页面
        this.shareScreen(
            onBack = navController::popBackStack,
            onBrowser = navController::navigateToBrowser
        )

        // 网页界面
        this.browserScreen(
            onBack = navController::popBackStack
        )
    }
}