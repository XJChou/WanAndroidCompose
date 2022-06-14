package com.zxj.wanandroid.compose.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.ui.ControlBean
import com.zxj.wanandroid.compose.ui.NavigationBar
import com.zxj.wanandroid.compose.ui.Toolbar
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import com.zxj.wanandroid.compose.application.getString


@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalDrawer(
        drawerContent = {
            DrawContent(navController)
        },
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
        drawerBackgroundColor = Color.Transparent,
        drawerElevation = 0.dp
    ) {
        val coroutineScope = rememberCoroutineScope()
        HomeContent(navController) {
            coroutineScope.launch {
                drawerState.open()
            }
        }
    }
}

@Composable
private fun DrawContent(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(WanAndroidTheme.colors.viewBackground)
            .padding(16.dp, 36.dp, 16.dp, 10.dp),
    ) {
        // 排名
        Icon(
            painter = painterResource(id = R.drawable.ic_rank_white_24dp),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.End)
                .clickable {
                    navController.navigate(NavigationRoute.LOGIN)
                },
            tint = WanAndroidTheme.colors.itemNavColorTv
        )

        // 圆形图边
        AsyncImage(
            model = "",
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .border(2.dp, Color.White, CircleShape)
                .align(Alignment.CenterHorizontally)
        )
        // 去登录
        Text(
            text = getString(R.string.go_login),
            modifier = Modifier
                .padding(12.dp),
            color = Color.White
        )


        // 登录前

        // 登录后
    }
}


@Preview
@Composable
fun PreviewDrawer() {
    DrawContent(rememberNavController())
}

@Composable
@OptIn(ExperimentalPagerApi::class)
fun HomeContent(navController: NavController, onMenuClickListener: () -> Unit) {
    val viewModel: HomeViewModel = viewModel(LocalContext.current as ComponentActivity)
    val pagerState = rememberPagerState()
    val animateScope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        // 菜单栏
        val leftControls = remember {
            arrayListOf(
                ControlBean(R.drawable.ic_menu_white_24dp) {
                    onMenuClickListener()
                }
            )
        }
        val rightControls = remember {
            arrayListOf(
                ControlBean(R.drawable.ic_search_white_24dp) {
                    navController.navigate(NavigationRoute.SEARCH) {
                        anim {
                            this.enter = androidx.fragment.R.animator.fragment_open_enter
                            this.exit = androidx.fragment.R.animator.fragment_open_enter
                            this.popEnter = androidx.fragment.R.animator.fragment_open_enter
                            this.popExit = androidx.fragment.R.animator.fragment_open_enter
                        }
                    }
                }
            )
        }
        Toolbar(
            modifier = Modifier.fillMaxWidth(),
            leftControl = leftControls,
            rightControl = rightControls
        )  {
            Text(
                text = viewModel.TITLE[pagerState.currentPage],
                fontSize = 18.sp,
                color = WanAndroidTheme.colors.itemTagTv
            )
        }
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


@Preview
@Composable
fun PreviewHome() {
    WanAndroidTheme {
        HomeScreen(rememberNavController())
    }
}