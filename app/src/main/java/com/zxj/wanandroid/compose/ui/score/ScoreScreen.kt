package com.zxj.wanandroid.compose.ui.score

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.data.bean.UserScoreBean
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.widget.*
import kotlinx.coroutines.launch

/**
 * 添加搜索结果界面到Navigation
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addScoreScreen(
    controller: NavHostController
) {
    composable(route = NavigationRoute.SCORE) {
        ScoreRoute(
            modifier = Modifier.fillMaxSize(1f),
            onBack = { controller.popBackStack() },
            onQuestion = {
                val url = "https://www.wanandroid.com/blog/show/2653"
                controller.navigate(NavigationRoute.buildBrowserRoute(url))
            }
        )
    }
}

@Composable
fun ScoreRoute(
    modifier: Modifier = Modifier,
    scoreViewModel: ScoreViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onQuestion: () -> Unit
) {
    val uiState by scoreViewModel.uiState.collectAsState()
    ScoreScreen(
        modifier = modifier,
        onBack = onBack,
        onQuestion = onQuestion,
        refresh = uiState.refresh,
        onRefresh = scoreViewModel::refresh,
        nextState = uiState.nextState,
        onNextPage = scoreViewModel::nextPage,
        dataList = uiState.dataList
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScoreScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onQuestion: () -> Unit,
    refresh: Boolean = false,
    onRefresh: () -> Unit = {},
    nextState: Int = NextState.STATE_NONE,
    onNextPage: () -> Unit = {},
    dataList: List<UserScoreBean>?
) {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.combinedClickable(
            onDoubleClick = {
                scope.launch {
                    state.scrollToItem(0, 0)
                }
            },
            onClick = {},
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )
    ) {

        TextToolBar(
            title = stringResource(id = R.string.nav_my_score),
            navigationIcon = {
                ToolBarIcon(R.drawable.ic_back, onBack)
            },
            actions = {
                ToolBarIcon(R.drawable.ic_help_white_24dp, onQuestion)
            }
        )

        SmartLazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            refreshState = rememberSwipeRefreshState(isRefreshing = false).also {
                it.isRefreshing = refresh
            },
            onRefresh = onRefresh,
            nextState = rememberNextState().also {
                it.state = nextState
            },
            onPageNext = onNextPage,
            state = state
        ) {
            item(contentType = 1) {
                Box(
                    modifier = Modifier
                        .background(WanAndroidTheme.colors.colorPrimary)
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Text(
                        text = "200",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 50.sp,
                        color = Color.White
                    )

                }
            }

            if (dataList != null) {
                items(dataList, key = { it.id }) {
                    ScoreItem(userScoreBean = it)
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewScoreScreen() {
    ScoreScreen(
        onBack = {},
        onQuestion = {},
        dataList = emptyList()
    )
}