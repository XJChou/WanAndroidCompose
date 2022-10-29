package com.zxj.wanandroid.compose.ui.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.composable
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.Screen
import com.zxj.wanandroid.compose.data.bean.User
import com.zxj.wanandroid.compose.ui.home.wechat.addHomeWechat
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.HomeViewModel
import com.zxj.wanandroid.compose.widget.NavigationItemBean
import com.zxj.wanandroid.compose.widget.TextToolBar
import com.zxj.wanandroid.compose.widget.ToolBarIcon
import kotlinx.coroutines.launch

/**
 * 添加主页界面到Navigation
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addHomeScreen(controller: NavHostController) {
    composable(route = Screen.Home.route) { backStackEntry ->
        HomeRoute(
            modifier = Modifier.fillMaxSize(),
            onNavigation = { it.navigation(controller) },
            onBrowser = { Screen.Browser.navigation(controller, it) }
        )
    }
}

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigation: (Screen) -> Unit,
    onBrowser: (String) -> Unit
) {
    val drawerUIState by viewModel.drawerUIState.collectAsState()
    HomeScreen(
        isLogin = drawerUIState.isLogin,
        user = drawerUIState.user,
        modifier = modifier,
        onBrowser = onBrowser,
        onSearch = { onNavigation(Screen.Search) },
        // 需要登录的
        onMineCollect = {
            if (drawerUIState.isLogin) {
                onNavigation(Screen.MineCollect)
            } else {
                onNavigation(Screen.Login)
            }
        },
        onMineScore = {
            if (drawerUIState.isLogin) {
                onNavigation(Screen.Score)
            } else {
                onNavigation(Screen.Login)
            }
        },
        onRankClick = {
            if (drawerUIState.isLogin) {
                onNavigation(Screen.Rank)
            } else {
                onNavigation(Screen.Login)
            }
        },
        onShareClick = {
            if (drawerUIState.isLogin) {
                onNavigation(Screen.Share)
            } else {
                onNavigation(Screen.Login)
            }
        },
        onSettingClick = {
            if (drawerUIState.isLogin) {
                // TODO
            } else {
                onNavigation(Screen.Login)
            }
        },
        onTodoClick = {
            if (drawerUIState.isLogin) {
                // TODO
            } else {
                onNavigation(Screen.Login)
            }
        },
        onSignOut = { viewModel.signOut() }
    )
}

// 数据层
val navigationItems = arrayOf(
    NavigationItemBean(R.drawable.ic_home_black_24dp, R.string.navigation_text_home),
    NavigationItemBean(R.drawable.ic_square_black_24dp, R.string.navigation_text_square),
    NavigationItemBean(R.drawable.ic_wechat_black_24dp, R.string.navigation_text_public),
    NavigationItemBean(R.drawable.ic_apps_black_24dp, R.string.navigation_text_system),
    NavigationItemBean(R.drawable.ic_project_black_24dp, R.string.navigation_text_project),
)
private val titles = arrayOf(
    R.string.toolbar_text_home,
    R.string.toolbar_text_square,
    R.string.toolbar_text_public,
    R.string.toolbar_text_system,
    R.string.toolbar_text_project
)
private val routes = arrayOf(
    Screen.HomeIndex,
    Screen.HomeSquare,
    Screen.HomeWechat,
    Screen.HomeSystem,
    Screen.HomeProject
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    isLogin: Boolean,
    user: User,
    modifier: Modifier = Modifier,
    onBrowser: (String) -> Unit,
    onMineCollect: () -> Unit,
    onMineScore: () -> Unit,
    onRankClick: () -> Unit,
    onTodoClick: () -> Unit,
    onSettingClick: () -> Unit,
    onShareClick: () -> Unit,
    onSearch: () -> Unit,
    onSignOut: () -> Unit
) {
    // 准备数据
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentPage by remember {
        derivedStateOf {
            routes.map { it.route }.indexOf( navBackStackEntry?.destination?.route ?: Screen.HomeIndex.route)
        }
    }
    val title by remember(titles) { derivedStateOf { titles[currentPage] } }


    ModalNavigationDrawer(
        drawerContent = {
            HomeDrawer(
                isLogin = isLogin,
                user = user,
                onSignOut = onSignOut,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(),
                onRankClick = onRankClick,
                onMineCollect = onMineCollect,
                onMineScore = onMineScore,
                onShareClick = onShareClick,
                onTodoClick = onTodoClick,
                onSettingClick = onSettingClick
            )
        },
        modifier = Modifier,
        drawerState = drawerState
    ) {
        // UI显示部分
        Scaffold(
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                HomeTopContent(
                    title = stringResource(id = title),
                    modifier = Modifier,
                    onDrawerToggle = {
                        scope.launch { drawerState.open() }
                    },
                    onSearch = onSearch
                )
            },
            bottomBar = {
                com.zxj.wanandroid.compose.ui.NavigationBar(
                    modifier = Modifier.padding(0.dp, 1.dp, 0.dp, 0.dp),
                    navigationItems = navigationItems,
                    selectIndex = currentPage,
                    onItemSelectedChanged = { routes[it].navigation(navController) }
                )
            },
            containerColor = WanAndroidTheme.colors.windowBackground,
            contentColor = WanAndroidTheme.colors.viewBackground,
        ) { innerPadding ->
            NavHost(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                navController = navController,
                startDestination = Screen.HomeIndex.route
            ) {
                addHomeIndex(onBrowser)
                addHomeSquare(controller = navController)
                addHomeWechat(controller = navController)
                addHomeSystem(controller = navController)
                addHomeProject(controller = navController)
            }
        }
    }
}

/**
 * 顶部内容
 */
@Composable
private fun HomeTopContent(
    title: String,
    modifier: Modifier = Modifier,
    onDrawerToggle: () -> Unit,
    onSearch: () -> Unit
) {
    TextToolBar(
        modifier = modifier,
        title = title,
        fitsSystemWindows = true,
        navigationIcon = {
            ToolBarIcon(drawableRes = R.drawable.ic_menu_white_24dp, onDrawerToggle)
        },
        actions = {
            ToolBarIcon(drawableRes = R.drawable.ic_search_white_24dp, onSearch)
        }
    )
}


