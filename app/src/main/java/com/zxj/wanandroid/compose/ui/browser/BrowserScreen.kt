package com.zxj.wanandroid.compose.ui.browser

import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.widget.TOOLBAR_HEIGHT
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

/**
 * 因WebView的存在，进场动画还存在问题
 */
@Composable
fun BrowserScreen(url: String, onBack: () -> Unit) {
    val webState = rememberWebViewState(url)
    // 这里使用Column会导致TextToolBar延迟了绘制
    Box(
        Modifier
            .background(WanAndroidTheme.colors.windowBackground)
            .fillMaxSize()
    ) {
        WebView(
            state = webState,
            modifier = Modifier
                .padding(top = TOOLBAR_HEIGHT)
                .statusBarsPadding()
                .fillMaxWidth(),
            onCreated = {
                it.settings.javaScriptEnabled = true
                it.webChromeClient = WebChromeClient()
                it.webViewClient = WebViewClient()
            }
        )
        TextToolBar(
            webState.pageTitle ?: "",
            true,
            navigationIcon = { ToolBarIcon(R.drawable.ic_back, onBack) }
        )
    }
}

@Preview
@Composable
fun PreviewBrowserScreen() {
    BrowserScreen("https://www.baidu.com") {

    }
}