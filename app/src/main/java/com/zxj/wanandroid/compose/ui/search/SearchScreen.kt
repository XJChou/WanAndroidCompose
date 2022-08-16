package com.zxj.wanandroid.compose.ui.search

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.zxj.wanandroid.compose.application.ComposeApplication
import com.zxj.wanandroid.compose.application.GetString
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
        SearchScreen(
            onBack = { controller.popBackStack() },
            onSearch = {
                controller.navigate(NavigationRoute.buildSearchResultRoute(it))
            },
        )
    }
}

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onSearch: (search: String) -> Unit
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
                    viewModel.dispatch(SearchViewAction.SearchHistoryAction(search))
                }
            },
            onSearch = {
                val search = searchState.value
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
            HotSearchPage(uiState.hotSearchList) {
                viewModel.dispatch(SearchViewAction.SearchHistoryAction(it.name))
            }
            // 历史搜索
            HistorySearchPage(
                searchHistoryList = uiState.searchHistoryList,
                onHistoryItemClick = {
                    viewModel.dispatch(SearchViewAction.SearchHistoryAction(it.search))
                },
                onHistoryItemDelete = {
                    viewModel.dispatch(SearchViewAction.DeleteHistoryAction(it))
                },
                onClearHistoryClick = {
                    viewModel.dispatch(SearchViewAction.ClearHistoryAction)
                }
            )
        }
    }

    LaunchedEffect(viewModel.uiEvent) {
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

@Preview
@Composable
fun PreviewSearch() {
    SearchScreen(
        onBack = {},
        onSearch = {},
    )
}