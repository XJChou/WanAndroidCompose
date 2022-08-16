package com.zxj.wanandroid.compose.ui.search

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.data.bean.Data
import com.zxj.wanandroid.compose.ui.screen.home.ArticleItem
import com.zxj.wanandroid.compose.widget.LoadingWheel
import com.zxj.wanandroid.compose.widget.TextToolBar
import com.zxj.wanandroid.compose.widget.ToolBarIcon
import java.sql.Ref

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
    ) { backStackEntry ->
        SearchResultRoute(onBack = { controller.popBackStack() })
    }
}

@Composable
fun SearchResultRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel()
) {
    val searchResultUiState by viewModel.uiState.collectAsState()
    SearchResultScreen(
        searchResultUiState.refresh,
        searchResultUiState.loadingUiState,
        searchResultUiState.data,
        content = viewModel.searchContext,
        onBack = onBack,
        onRefresh = { viewModel.refresh() },
        onNextPage = { viewModel.nextPage() },
        modifier
    )
}

@VisibleForTesting
@Composable
internal fun SearchResultScreen(
    refresh: Boolean,
    loadingUiState: LoadingUiState,
    data: List<Data>,
    content: String,
    onBack: () -> Unit,
    onRefresh: () -> Unit,
    onNextPage: () -> Unit,
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

        // 自动刷新处理
        val listState = rememberLazyListState()
        val visibleItemsInfo by remember { derivedStateOf { listState.layoutInfo.visibleItemsInfo } }
        if (visibleItemsInfo.lastOrNull()?.key == Int.MIN_VALUE && loadingUiState is LoadingUiState.Finish && !loadingUiState.over) {
            onNextPage()
        }

        SwipeRefresh(state = refreshState, onRefresh = onRefresh) {
            LazyColumn(
                state = listState,
                content = {
                    items(data, contentType = { Data::class }) {
                        ArticleItem(it, { _, _ -> }, { _ -> }, modifier.padding(top = 2.dp))
                    }
                    when (loadingUiState) {
                        is LoadingUiState.Loading -> {
                            item(contentType = LoadingUiState.Loading) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(1f)
                                        .height(48.dp)
                                ) {
                                    LoadingWheel(
                                        "加载中",
                                        Modifier
                                            .size(36.dp)
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        }
                        is LoadingUiState.Finish -> {
                            item(key = Int.MIN_VALUE) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(1f)
                                        .height(48.dp)
                                ) {
                                    Text(
                                        text = if (loadingUiState.over) "已全部加载" else "加载成功",
                                        modifier = Modifier.fillMaxSize(1f),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        is LoadingUiState.Failed -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(1f)
                                        .height(48.dp)
                                ) {
                                    Text(
                                        text = "加载失败",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clickable { onNextPage() },
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Preview
@Composable
fun PreviewSearchResultScreen() {
    SearchResultScreen(
        false,
        LoadingUiState.Init,
        emptyList(),
        content = "测试",
        onBack = {},
        modifier = Modifier,
        onRefresh = {},
        onNextPage = {}
    )
}