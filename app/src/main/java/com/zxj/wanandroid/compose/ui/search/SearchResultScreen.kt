package com.zxj.wanandroid.compose.ui.search

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.data.bean.Data
import com.zxj.wanandroid.compose.widget.*

/**
 * 添加搜索结果界面到Navigation
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addSearchResultScreen(
    controller: NavHostController
) {
    composable(
        route = NavigationRoute.SEARCH_RESULT,
        listOf(navArgument("content") { type = NavType.StringType }),
    ) {
        SearchResultRoute(
            onBack = { controller.popBackStack() },
            onItemClick = { controller.navigate(NavigationRoute.buildBrowserRoute(it.link)) }
        )
    }
}

@Composable
fun SearchResultRoute(
    onBack: () -> Unit,
    onItemClick: (Data) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel()
) {
    val searchResultUiState by viewModel.uiState.collectAsState()
    SearchResultScreen(
        searchResultUiState.refresh,
        searchResultUiState.nextState,
        searchResultUiState.data,
        content = viewModel.searchContext,
        onBack = onBack,
        onRefresh = { viewModel.refresh() },
        onPageNext = { viewModel.nextPage() },
        onItemClick = onItemClick,
        modifier
    )
}

@VisibleForTesting
@Composable
internal fun SearchResultScreen(
    refresh: Boolean,
    nextPageState: Int,
    data: List<Data>,
    content: String,
    onBack: () -> Unit,
    onRefresh: () -> Unit,
    onPageNext: () -> Unit,
    onItemClick: (Data) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {
        // 状态栏
        TextToolBar(
            content,
            navigationIcon = {
                ToolBarIcon(drawableRes = R.drawable.ic_back, onBack)
            }
        )

        // 列表显示
        val refreshState = rememberSwipeRefreshState(false)
        refreshState.isRefreshing = refresh
        val nextState = rememberNextState()
        nextState.state = nextPageState

        SmartLazyColumn(
            refreshState = refreshState, onRefresh = onRefresh,
            nextState = nextState, onPageNext = onPageNext,
            modifier = Modifier.fillMaxSize()
        ) {
            items(data, key = { it.id }, contentType = { it::class }) {
                ArticleItem(
                    data = it,
                    onItemZanClick = { _, _ -> },
                    onItemClick = onItemClick,
                    modifier = modifier.padding(top = 2.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewSearchResultScreen() {
    SearchResultScreen(
        false,
        NextState.STATE_NONE,
        emptyList(),
        content = "测试",
        onBack = {},
        modifier = Modifier,
        onRefresh = {},
        onPageNext = {},
        onItemClick = {}
    )
}