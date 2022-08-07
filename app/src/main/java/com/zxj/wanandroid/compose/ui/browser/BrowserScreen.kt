package com.zxj.wanandroid.compose.ui.browser

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.widget.TextToolBar
import com.zxj.wanandroid.compose.widget.ToolBarIcon

/**
 * 添加浏览界面到Navigation
 * @param webUrl 浏览的地址
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addBrowserScreen(controller: NavHostController) {
    composable(
        route = NavigationRoute.BROWSER,
        listOf(navArgument("webUrl") { type = NavType.StringType }),
//        deepLinks = listOf(navDeepLink { uriPattern })
    ) { backStackEntry ->
        BrowserScreen(
            url = backStackEntry.arguments!!.getString("webUrl")!!,
            onBack = { controller.popBackStack() }
        )
    }
}

@Composable
fun BrowserScreen(url: String, onBack: () -> Unit) {
    val webState = rememberWebViewState(url = url)
    Column {
        TextToolBar(
            webState.pageTitle ?: "",
            true,
            navigationIcon = { ToolBarIcon(R.drawable.ic_back, onBack) }
        )
        WebView(
            state = webState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}