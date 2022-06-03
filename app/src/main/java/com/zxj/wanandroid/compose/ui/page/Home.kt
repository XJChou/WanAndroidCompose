package com.zxj.wanandroid.compose.ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.ui.ControlBean
import com.zxj.wanandroid.compose.ui.NavigationBar
import com.zxj.wanandroid.compose.ui.Toolbar
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Home() {
    val viewModel: HomeViewModel = viewModel()
    val pagerState = rememberPagerState()
    val animateScope = rememberCoroutineScope()
    Box(Modifier.fillMaxSize()) {
        // 上部分
        Column(Modifier.fillMaxSize()) {
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
                    .fillMaxWidth(),
                leftControl = leftControls,
                title = viewModel.TITLE[pagerState.currentPage],
                titleColor = WanAndroidTheme.colors.itemTagTv,
                rightControl = rightControls
            )
            HomePager(
                count = viewModel.navigationItems.size,
                pagerState = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            // 底部导航
            NavigationBar(
                Modifier.padding(0.dp, 1.dp, 0.dp, 0.dp),
                viewModel.navigationItems,
                pagerState.currentPage
            ) {
                animateScope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
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