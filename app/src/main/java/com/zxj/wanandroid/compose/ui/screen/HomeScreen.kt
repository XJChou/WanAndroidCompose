package com.zxj.wanandroid.compose.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.GetString
import com.zxj.wanandroid.compose.ui.ControlBean
import com.zxj.wanandroid.compose.ui.NavigationBar
import com.zxj.wanandroid.compose.ui.Toolbar
import com.zxj.wanandroid.compose.ui.screen.home.*
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.DrawerUIState
import com.zxj.wanandroid.compose.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigation: (String) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val drawerUIState by viewModel.drawerUIState.collectAsState()
    ModalDrawer(
        drawerContent = {
            DrawHead(navigation)
            DrawContent(
                drawerUIState,
                navigation = navigation,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        },
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
        drawerBackgroundColor = WanAndroidTheme.colors.viewBackground,
    ) {
        HomeContent(navigation) {
            coroutineScope.launch { drawerState.open() }
        }
    }
}

@Composable
private fun DrawHead(navigation: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(WanAndroidTheme.colors.colorPrimary)
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
                },
            tint = Color.White
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
            text = GetString(R.string.go_login),
            modifier = Modifier
                .padding(0.dp, 12.dp, 0.dp, 0.dp)
                .align(Alignment.CenterHorizontally),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .padding(0.dp, 8.dp, 0.dp, 0.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = GetString(id = R.string.nav_id),
                color = Color(0xFFF5F5F5),
                fontSize = 12.sp
            )
            Text(
                text = GetString(id = R.string.nav_line_4),
                fontSize = 12.sp,
                color = Color(0xFFF5F5F5),
            )
        }

        Row(
            modifier = Modifier
                .padding(0.dp, 8.dp, 0.dp, 0.dp)
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = GetString(id = R.string.nav_grade),
                color = Color(0xFFF5F5F5),
                fontSize = 12.sp
            )
            Text(
                text = GetString(id = R.string.nav_line_2),
                fontSize = 12.sp,
                color = Color(0xFFF5F5F5),
            )
            Text(
                text = GetString(id = R.string.nav_rank),
                modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp),
                fontSize = 12.sp,
                color = Color(0xFFF5F5F5),
            )
            Text(
                text = GetString(id = R.string.nav_line_2),
                fontSize = 12.sp,
                color = Color(0xFFF5F5F5),
            )
        }
    }
}

@Composable
private fun DrawContent(
    drawerUIState: DrawerUIState,
    navigation: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier
            .background(WanAndroidTheme.colors.viewBackground)
            .padding(0.dp, 10.dp, 0.dp, 0.dp)
    ) {
        DrawItemContent(R.drawable.ic_score_white_24dp, GetString(id = R.string.nav_my_score)) {
            drawerUIState.ifLogin(navigation) {

            }
        }
        DrawItemContent(R.drawable.ic_like_not, GetString(id = R.string.nav_my_collect)) {
            drawerUIState.ifLogin(navigation) {

            }
        }
        DrawItemContent(R.drawable.ic_share_white_24dp, GetString(id = R.string.my_share)) {
            drawerUIState.ifLogin(navigation) {

            }
        }
        DrawItemContent(R.drawable.ic_todo_default_24dp, GetString(id = R.string.nav_todo)) {
            drawerUIState.ifLogin(navigation) {

            }
        }
        DrawItemContent(R.drawable.ic_night_24dp, GetString(id = R.string.nav_night_mode)) {
            WanAndroidTheme.changeTheme()
        }
        DrawItemContent(R.drawable.ic_setting_24dp, GetString(id = R.string.nav_setting)) {
            drawerUIState.ifLogin(navigation) {

            }
        }
        if (drawerUIState.isLogin) {
            DrawItemContent(R.drawable.ic_logout_white_24dp, GetString(id = R.string.nav_logout)) {
//            navigation("")
            }
        }
    }
}

private fun DrawerUIState.ifLogin(navigation: (String) -> Unit, block: () -> Unit) {
    if (isLogin) {
        block()
    } else {
        navigation(NavigationRoute.LOGIN)
    }
}

@Composable
private fun DrawItemContent(@DrawableRes icon: Int, title: String, itemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { itemClick() }
            .padding(20.dp, 0.dp, 0.dp, 0.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = WanAndroidTheme.colors.itemNavColorTv,
            modifier = Modifier.padding(0.dp, 15.dp)
        )
        Text(
            text = title,
            color = WanAndroidTheme.colors.itemNavColorTv,
            fontSize = 16.sp,
            modifier = Modifier.padding(40.dp, 0.dp, 0.dp, 0.dp),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
fun HomeContent(navigation: (String) -> Unit, onMenuClickListener: () -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
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
                    navigation(NavigationRoute.SEARCH)
                }
            )
        }
        Toolbar(
            modifier = Modifier.fillMaxWidth(),
            leftControl = leftControls,
            rightControl = rightControls
        ) {
            Text(
                text = viewModel.TITLE[pagerState.currentPage],
                fontSize = 18.sp,
                color = WanAndroidTheme.colors.itemTagTv
            )
        }
        HorizontalPager(
            count = viewModel.navigationItems.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        {
            when (it) {
                0 -> IndexPage()
                1 -> SquarePage()
                2 -> PublicPage()
                3 -> SystemPage()
                4 -> ProjectPage()
            }
        }
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
        HomeScreen() {
        }
    }
}
