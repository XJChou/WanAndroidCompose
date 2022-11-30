package com.zxj.article.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun HomeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit,
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = indexRoute
    ) {
        this.indexScreen(navigateToBrowser)
        this.wechatScreen(navigateToBrowser)
        this.squareScreen(navigateToBrowser)
        this.systemScreen(navigateToBrowser)
        this.projectScreen(navigateToBrowser)
    }
}