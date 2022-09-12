package com.zxj.wanandroid.compose.ui.rank

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.data.bean.CoinInfoBean
import com.zxj.wanandroid.compose.data.bean.coinInfoBeanDemoData
import com.zxj.wanandroid.compose.ui.score.ScoreViewModel
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.widget.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addRankScreen(
    controller: NavHostController
) {
    composable(route = NavigationRoute.RANK) {
        RankRoute(
            onBack = { controller.popBackStack() },
            modifier = Modifier
        )
    }
}

@Composable
fun RankRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RankViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    RankScreen(
        onBack = onBack,
        modifier = modifier.fillMaxSize(1f),
        onRefresh = viewModel::refresh,
        refresh = uiState.refresh,
        onNextPage = viewModel::nextPage,
        nextState = uiState.nextState,
        rankList = uiState.dataList
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun RankScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    rankList: List<CoinInfoBean>? = emptyList(),
    refresh: Boolean,
    onRefresh: () -> Unit = {},
    nextState: Int,
    onNextPage: () -> Unit = {},
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
            title = stringResource(id = R.string.score_list),
            navigationIcon = {
                ToolBarIcon(drawableRes = R.drawable.ic_back, onBack)
            }
        )
        SmartLazyColumn(
            refreshState = rememberSwipeRefreshState(isRefreshing = refresh),
            onRefresh = onRefresh,
            nextState = rememberNextState(nextState),
            onPageNext = onNextPage,
            state = state
        ) {
            if (rankList != null) {
                if (rankList.isNotEmpty()) {
                    items(rankList, key = { it.userId }) {
                        RankItem(coinInfoBean = it)
                    }
                } else {
                    item(contentType = { 1 }) {
                        EmptyData(modifier = modifier.fillParentMaxSize())
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRankScreen() {
    WanAndroidTheme {
        RankScreen(
            onBack = {},
            modifier = Modifier,
            rankList = listOf(coinInfoBeanDemoData),
            refresh = false,
            onRefresh = {},
            nextState = NextState.STATE_NONE,
            onNextPage = {}
        )
    }
}