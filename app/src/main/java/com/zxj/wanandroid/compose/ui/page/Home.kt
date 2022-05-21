package com.zxj.wanandroid.compose.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.getString
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.ui.ControlBean
import com.zxj.wanandroid.compose.ui.NavigationBar
import com.zxj.wanandroid.compose.ui.Toolbar
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.HomeViewModel

val TITLE = arrayOf(
    getString(R.string.toolbar_text_home),
    getString(R.string.toolbar_text_square),
    getString(R.string.toolbar_text_public),
    getString(R.string.toolbar_text_system),
    getString(R.string.toolbar_text_project)
)

@Composable
fun Home() {
    val viewModel: HomeViewModel = viewModel()
    var selectIndex by remember { mutableStateOf(0) }
    Box(Modifier.fillMaxSize()) {
        // 上部分
        Box(Modifier.matchParentSize()) {
            // 列表
            when (selectIndex) {
                0 -> IndexPage()
                1 -> SquarePage()
                2 -> PublicPage()
                3 -> SystemPage()
                4 -> ProjectPage()
            }
        }

        // 菜单栏
        val leftControls = remember {
            arrayListOf(
                ControlBean(R.drawable.ic_menu_white_24dp) {
                    toast("菜单")
                    viewModel.theme = if (viewModel.theme == WanAndroidTheme.Theme.Night) {
                        WanAndroidTheme.Theme.Normal
                    } else {
                        WanAndroidTheme.Theme.Night
                    }
                }
            )
        }
        val rightControls = remember {
            arrayListOf(
                ControlBean(R.drawable.ic_search_white_24dp) {
                    toast("搜索")
                }
            )
        }
        Toolbar(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(),
            leftControl = leftControls,
            title = TITLE[selectIndex],
            titleColor = WanAndroidTheme.colors.itemTagTv,
            rightControl = rightControls
        )

        // 底部导航
        NavigationBar(Modifier.align(Alignment.BottomStart), selectIndex) {
            selectIndex = it
        }
    }
}

@Preview
@Composable
fun PreviewHome() {
    WanAndroidTheme {
        Home()
    }
}