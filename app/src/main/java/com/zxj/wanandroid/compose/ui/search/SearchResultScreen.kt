package com.zxj.wanandroid.compose.ui.search

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.data.bean.Article
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
            onItemClick = { controller.navigate(NavigationRoute.buildBrowserRoute(it.link)) },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun SearchResultRoute(
    onBack: () -> Unit,
    onItemClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel()
) {
    val pagingItems = viewModel.pagingItems.collectAsLazyPagingItems()
    SearchResultScreen(
        content = viewModel.searchContext,
        onBack = onBack,
        onItemClick = onItemClick,
        onItemZan = { collect, data -> viewModel.dealZanAction(collect, data) },
        pagingItems = pagingItems,
        modifier = modifier
    )
    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect {
            when (it) {
                is UIEvent.ShowToast -> {
                    toast(it.msg)
                }
            }
        }
    }
}

@VisibleForTesting
@Composable
internal fun SearchResultScreen(
    content: String,
    onBack: () -> Unit,
    onItemClick: (Article) -> Unit,
    onItemZan: (Boolean, Article) -> Unit,
    pagingItems: LazyPagingItems<Article>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TextToolBar(
                content,
                navigationIcon = {
                    ToolBarIcon(drawableRes = R.drawable.ic_back, onBack)
                }
            )
        }
    ) { padding ->
        PagingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            pagingItems = pagingItems,
            padding = padding,
        ) {
            items(pagingItems, key = { it.id }) {
                ArticleItem(
                    data = it!!,
                    onItemZanClick = onItemZan,
                    onItemClick = onItemClick,
                    modifier = modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
