package com.zxj.wanandroid.compose.ui.browser

import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.Screen
import com.zxj.wanandroid.compose.widget.TextToolBar
import com.zxj.wanandroid.compose.widget.ToolBarIcon
import java.net.URLDecoder

/**
 * 添加浏览界面到Navigation
 * @param webUrl 浏览的地址
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addBrowserScreen(controller: NavHostController) {
    composable(
        route = Screen.Browser.route,
        arguments = listOf(navArgument("url") { type = NavType.StringType }),
    ) { backStackEntry ->
        val url = URLDecoder.decode(
            backStackEntry.arguments!!.getString("url")!!,
            Charsets.UTF_8.toString()
        )
        BrowserScreen(
            url = url,
            onBack = { controller.popBackStack() }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserScreen(url: String, onBack: () -> Unit) {
    val webState = rememberWebViewState(url)
    Scaffold(
        modifier = Modifier,
        topBar = {
            TextToolBar(
                title = webState.pageTitle ?: "",
                navigationIcon = { ToolBarIcon(R.drawable.ic_back, onBack) }
            )
        }
    ) {
        WebView(
            state = webState,
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            onCreated = {
                it.settings.javaScriptEnabled = true
                it.webChromeClient = WebChromeClient()
                it.webViewClient = WebViewClient()
            }
        )
    }
}

@Preview
@Composable
private fun PreviewBrowserScreen() {
    BrowserScreen(
        url = "https://www.baidu.com",
        onBack = {}
    )
}