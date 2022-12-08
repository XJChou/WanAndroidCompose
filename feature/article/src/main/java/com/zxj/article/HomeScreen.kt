package com.zxj.article

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.zxj.article.components.HomeDrawer
import com.zxj.article.components.NavigationItemBean
import com.zxj.article.navigation.*
import com.zxj.article.navigation.HomeNavHost
import com.zxj.article.navigation.navigateToIndex
import com.zxj.article.navigation.navigateToSquare
import com.zxj.article.navigation.navigateToWechat
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.Knowledge
import com.zxj.model.USER_EMPTY
import com.zxj.model.UserBean
import com.zxj.ui.TextToolBar
import com.zxj.ui.ToolBarIcon
import kotlinx.coroutines.launch
import com.zxj.ui.R.drawable as UIDrawable

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSearch: () -> Unit,
    navigateToCollect: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToScore: () -> Unit,
    navigateToRank: () -> Unit,
    navigateToShare: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToTODO: () -> Unit,
    navigateToKnowledgeSystemDetail: (String, List<Knowledge>) -> Unit,
    navigateToBrowser: (String) -> Unit
) {
    val user by viewModel.userFlow.collectAsState()
    HomeScreen(
        user = user,
        modifier = modifier,
        navigateToBrowser = navigateToBrowser,
        navigateToSearch = navigateToSearch,
        // 需要登录的
        navigateToCollect = {
            if (user !== USER_EMPTY) {
                navigateToCollect()
            } else {
                navigateToLogin()
            }
        },
        navigateToScore = {
            if (user !== USER_EMPTY) {
                navigateToScore()
            } else {
                navigateToLogin()
            }
        },
        navigateToRank = {
            if (user !== USER_EMPTY) {
                navigateToRank()
            } else {
                navigateToLogin()
            }
        },
        navigateToShare = {
            if (user !== USER_EMPTY) {
                navigateToShare()
            } else {
                navigateToLogin()
            }
        },
        navigateToSetting = {
            if (user !== USER_EMPTY) {
                navigateToSetting()
            } else {
                navigateToLogin()
            }
        },
        navigateToTODO = {
            if (user !== USER_EMPTY) {
                navigateToTODO()
            } else {
                navigateToLogin()
            }
        },
        navigateToKnowledgeSystemDetail = navigateToKnowledgeSystemDetail,
        onSignOut = viewModel::signOut
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
    indexRoute, squareRoute, wechatRoute, systemRoute, projectRoute
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun HomeScreen(
    user: UserBean,
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToCollect: () -> Unit,
    navigateToScore: () -> Unit,
    navigateToRank: () -> Unit,
    navigateToShare: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToTODO: () -> Unit,
    navigateToKnowledgeSystemDetail: (String, List<Knowledge>) -> Unit,
    onSignOut: () -> Unit
) {
    // 准备数据
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentPage by remember {
        derivedStateOf { routes.indexOf(navBackStackEntry?.destination?.route ?: indexRoute) }
    }
    val title by remember(titles) { derivedStateOf { titles[currentPage] } }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            HomeDrawer(
                user = user,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(),
                navigateToRank = navigateToRank,
                navigateToCollect = navigateToCollect,
                navigateToScore = navigateToScore,
                navigateToShare = navigateToShare,
                navigateToTODO = navigateToTODO,
                navigateToSetting = navigateToSetting,
                onSignOut = onSignOut,
            )
        },
        modifier = modifier,
    ) {
        // UI显示部分
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TextToolBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = title),
                    fitsSystemWindows = true,
                    navigationIcon = {
                        ToolBarIcon(drawableRes = R.drawable.ic_menu_white_24dp) {
                            scope.launch { drawerState.open() }
                        }
                    },
                    actions = {
                        ToolBarIcon(drawableRes = UIDrawable.ic_search_white_24dp, navigateToSearch)
                    }
                )
            },
            bottomBar = {
                com.zxj.article.components.NavigationBar(
                    modifier = Modifier.padding(0.dp, 1.dp, 0.dp, 0.dp),
                    navigationItems = navigationItems,
                    selectIndex = currentPage,
                    onItemSelectedChanged = {
                        when (it) {
                            0 -> navController.navigateToIndex()
                            1 -> navController.navigateToSquare()
                            2 -> navController.navigateToWechat()
                            3 -> navController.navigateToSystem()
                            4 -> navController.navigateToProject()
                        }
                    }
                )
            },
            containerColor = WanAndroidTheme.colors.windowBackground,
            contentColor = WanAndroidTheme.colors.viewBackground,
        ) { innerPadding ->
            HomeNavHost(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                navController = navController,
                navigateToBrowser = navigateToBrowser,
                navigateToKnowledgeSystemDetail = navigateToKnowledgeSystemDetail
            )
        }
    }
}
