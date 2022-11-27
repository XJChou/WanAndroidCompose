package com.zxj.article.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
internal fun HomeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit,
) {
    NavHost(
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