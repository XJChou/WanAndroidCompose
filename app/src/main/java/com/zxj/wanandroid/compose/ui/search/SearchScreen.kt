package com.zxj.wanandroid.compose.ui.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.widget.TextToolBar
import com.zxj.wanandroid.compose.widget.ToolBarIcon


/**
 * 添加搜索界面到Navigation
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addSearchScreen(controller: NavHostController) {
    composable(route = NavigationRoute.SEARCH) { backStackEntry ->
        SearchScreen(
            onBack = { controller.popBackStack() }
        )
    }
}

@Composable
fun SearchScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
//        BasicTextField(
//            modifier = Modifier.weight(1f),
//            singleLine = true,
//            value = "value",
//            onValueChange = {
//
//            })
        TextToolBar(
            title = "value",
            navigationIcon = {
                ToolBarIcon(drawableRes = R.drawable.ic_back, onBack)
            },
            actions = {
                ToolBarIcon(drawableRes = R.drawable.ic_search) {
                    toast("搜索")
                }
            }
        )
    }
}

@Preview
@Composable
fun SearchPrev() {
    SearchScreen {

    }
}