package com.zxj.wanandroid.compose.ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zxj.wanandroid.compose.NavigationRoute
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
fun Home(/*navController: NavController*/) {
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
                        WanAndroidTheme.changeTheme()
                    }
                )
            }
            val rightControls = remember {
                arrayListOf(
                    ControlBean(R.drawable.ic_search_white_24dp) {
//                        navController.navigate(NavigationRoute.SEARCH) {
//                            anim {
//                                this.enter = androidx.fragment.R.anim.fragment_open_enter
//                                this.exit = androidx.fragment.R.anim.fragment_open_enter
//                                this.popEnter = androidx.fragment.R.anim.fragment_open_enter
//                                this.popExit = androidx.fragment.R.anim.fragment_open_enter
//                            }
//                        }
                    }
                )
            }
            Toolbar(
                modifier = Modifier
                    .fillMaxWidth(),
                leftControl = leftControls,
                {
                    Text(
                        text = viewModel.TITLE[pagerState.currentPage],
                        fontSize = 18.sp,
                        color = WanAndroidTheme.colors.itemTagTv
                    )
                },
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