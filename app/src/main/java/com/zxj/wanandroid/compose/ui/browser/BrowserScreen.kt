package com.zxj.wanandroid.compose.ui.browser

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.GetString
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.widget.ControlBean
import com.zxj.wanandroid.compose.widget.Toolbar

/**
 * 添加浏览界面到Navigation
 * @param webUrl 浏览的地址
 */
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
        // ToolBar
        Toolbar(
            modifier = Modifier.fillMaxWidth(),
            leftControl = arrayListOf(ControlBean(R.drawable.ic_back, contentDescription = "返回") {
                onBack()
            }),
        ) {
            Text(
                text = webState.pageTitle ?: "",
                fontSize = 18.sp,
                color = WanAndroidTheme.colors.itemTagTv
            )
        }
        //
        WebView(
            state = webState,
            Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}