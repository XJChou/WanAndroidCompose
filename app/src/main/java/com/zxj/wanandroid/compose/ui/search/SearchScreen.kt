package com.zxj.wanandroid.compose.ui.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.GetString
import com.zxj.wanandroid.compose.data.bean.HistorySearchBean
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.widget.SearchToolBar
import com.zxj.wanandroid.compose.widget.ToolBarIcon
import com.zxj.wanandroid.compose.widget.rememberSearchState


/**
 * 添加搜索界面到Navigation
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addSearchScreen(controller: NavHostController) {
    composable(route = NavigationRoute.SEARCH) { backStackEntry ->
        SearchScreen(
            onBack = { controller.popBackStack() },
            onSearch = { },
            onHistoryItemClick = {}
        )
    }
}

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onSearch: (search: String) -> Unit,
    onHistoryItemClick: (HistorySearchBean) -> Unit
) {
    val viewModel: SearchViewModel = hiltViewModel()
    Column {
        // ToolBar
        val searchState = rememberSearchState(value = "")
        SearchToolBar(
            searchState,
            GetString(id = R.string.search_tint),
            navigationIcon = {
                ToolBarIcon(drawableRes = R.drawable.ic_back, onBack)
            },
            actions = {
                ToolBarIcon(drawableRes = R.drawable.ic_search_white_24dp) {
                    val search = searchState.value
                    onSearch(search)
                    viewModel.dispatch(SearchViewAction.SearchHistoryAction(search))
                }
            },
            onSearch = {
                val search = searchState.value
                onSearch(search)
                viewModel.dispatch(SearchViewAction.SearchHistoryAction(search))
            }
        )
        // .verticalScroll(rememberScrollState())
        Column(
            modifier = Modifier
                .background(WanAndroidTheme.colors.viewBackground)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // 内容文本
            val uiState by viewModel.uiState.collectAsState()
            // 热门搜索
            HotSearchPage(uiState.hotSearchList)
            // 历史搜索
            HistorySearchPage(
                uiState.searchHistoryList,
                onHistoryItemClick = onHistoryItemClick,
                onHistoryItemDelete = {
                    viewModel.dispatch(SearchViewAction.DeleteHistoryAction(it))
                },
                onClearHistoryClick = {
                    viewModel.dispatch(SearchViewAction.ClearHistoryAction)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearch() {
    SearchScreen(
        onBack = {},
        onSearch = {},
        onHistoryItemClick = {}
    )
}