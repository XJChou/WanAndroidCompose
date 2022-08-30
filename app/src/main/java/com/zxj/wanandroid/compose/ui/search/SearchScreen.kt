package com.zxj.wanandroid.compose.ui.search

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.ComposeApplication
import com.zxj.wanandroid.compose.data.bean.HistorySearchBean
import com.zxj.wanandroid.compose.data.bean.HotSearchBean
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.widget.SearchToolBar
import com.zxj.wanandroid.compose.widget.ToolBarIcon
import com.zxj.wanandroid.compose.widget.rememberSearchState
import kotlinx.coroutines.launch


/**
 * 添加搜索界面到Navigation
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addSearchScreen(controller: NavHostController) {
    composable(route = NavigationRoute.SEARCH) { backStackEntry ->
        SearchRoute(
            onBack = { controller.popBackStack() },
            onSearch = {
                controller.navigate(NavigationRoute.buildSearchResultRoute(it))
            }
        )
    }
}

/**
 * 屏蔽viewModel的存在
 */
@Composable
fun SearchRoute(
    onBack: () -> Unit,
    onSearch: (search: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchViewState by viewModel.uiState.collectAsState()
    SearchScreen(
        modifier = modifier,
        searchHistoryList = searchViewState.searchHistoryList,
        hotSearchList = searchViewState.hotSearchList,
        hotSearchColorList = searchViewState.hotSearchColorList,
        onBack = onBack,
        onSearch = {
            viewModel.search(it)
        },
        onClearHistory = {
            viewModel.clearHistorySearch()
        },
        onHistoryClick = {
            viewModel.search(it.search)
        },
        onHistoryDelete = {
            viewModel.deleteHistorySearch(it)
        }
    )

    LaunchedEffect(Unit) {
        launch {
            viewModel.uiEvent.collect {
                when (it) {
                    is SearchViewEvent.ShowToast -> {
                        Toast.makeText(ComposeApplication.application, it.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is SearchViewEvent.SearchSuccess -> {
                        onSearch(it.search)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchHistoryList: List<HistorySearchBean> = emptyList(),
    hotSearchList: List<HotSearchBean> = emptyList(),
    hotSearchColorList: List<Color> = emptyList(),
    onBack: () -> Unit,
    onSearch: (search: String) -> Unit,
    onClearHistory: () -> Unit,
    onHistoryClick: (HistorySearchBean) -> Unit,
    onHistoryDelete: (HistorySearchBean) -> Unit,
) {
    Column(modifier) {
        val searchState = rememberSearchState(value = "")
        SearchToolBar(
            searchState,
            stringResource(id = R.string.search_tint),
            navigationIcon = {
                ToolBarIcon(drawableRes = R.drawable.ic_back, onBack)
            },
            actions = {
                ToolBarIcon(drawableRes = R.drawable.ic_search_white_24dp) {
                    onSearch(searchState.value)
                }
            },
            onSearch = onSearch
        )

        Column(
            modifier = Modifier
                .background(WanAndroidTheme.colors.viewBackground)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // 热门搜索
            HotSearchPage(hotSearchList, hotSearchColorList) { onSearch(it.name) }
            // 历史搜索
            HistorySearchPage(
                searchHistoryList = searchHistoryList,
                onHistoryItemClick = onHistoryClick,
                onHistoryItemDelete = onHistoryDelete,
                onClearHistoryClick = onClearHistory
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearch() {
    SearchRoute(
        onBack = {},
        onSearch = {}
    )
}