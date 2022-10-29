package com.zxj.wanandroid.compose

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
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
import java.net.URLEncoder

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


    object Search : Screen("/search")
    object Register : Screen("/register")
    object MineCollect : Screen("/mine/collect")
    object Score : Screen("/mine/score")
    object Rank : Screen("/user/rank")
    object Share : Screen("/user/share")
    object Login : Screen("/user/login")
    object SearchDetails : Screen("/app/search/result/{content}") {
        fun search(content: String) = "/app/search/result/${content}"
    }

    // 浏览器
    object Browser : Screen("/browser/{url}") {

        fun navigation(controller: NavController, url: String) {
            controller.navigate("/browser/${url.encode()}")
        }

        private fun String.encode(): String {
            return URLEncoder.encode(this, Charsets.UTF_8.toString())
        }
    }

    // 主页相关
    object Home : Screen("/home")
    object HomeIndex : SaveStateScreen("/home/index")
    object HomeSquare : SaveStateScreen("/home/square")
    object HomeWechat : SaveStateScreen("/home/wechat")
    object HomeSystem : SaveStateScreen("/home/system")
    object HomeProject : SaveStateScreen("/home/project")

    /**
     * 需要保存状态
     */
    open class SaveStateScreen(route: String) : Screen(route) {
        override fun navigation(controller: NavController) {
            controller.navigate(route) {
                this.popUpTo(controller.graph.findStartDestination().id) {
                    saveState = true
                }
                this.restoreState = true
                this.launchSingleTop = true
            }
        }
    }

    /**
     * 普通跳转
     */
    open fun navigation(controller: NavController) {
        controller.navigate(route)
    }
}