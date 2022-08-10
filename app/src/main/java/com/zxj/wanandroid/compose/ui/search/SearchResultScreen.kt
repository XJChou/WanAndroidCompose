package com.zxj.wanandroid.compose.ui.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.widget.TextToolBar
import com.zxj.wanandroid.compose.widget.ToolBarIcon

/**
 * 添加搜索结果界面到Navigation
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addSearchResultScreen(controller: NavHostController) {
    composable(
        route = NavigationRoute.SEARCH_RESULT,
        listOf(navArgument("content") { type = NavType.StringType }),
    ) { backStackEntry ->
        SearchResultScreen(
            content = backStackEntry.arguments!!.getString("content")!!,
            onBack = { controller.popBackStack() },
        )
    }
}

@Composable
fun SearchResultScreen(
    content: String,
    onBack: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        TextToolBar(
            content,
            navigationIcon = {
                ToolBarIcon(drawableRes = R.drawable.ic_back, onBack)
            }
        )
    }
}

@Preview
@Composable
fun PreviewSearchResultScreen() {
    SearchResultScreen("测试") {}
}