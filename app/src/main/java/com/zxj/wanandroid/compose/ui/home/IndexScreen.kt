package com.zxj.wanandroid.compose.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zxj.wanandroid.compose.Screen
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.data.bean.Article
import com.zxj.wanandroid.compose.data.bean.BannerBean
import com.zxj.wanandroid.compose.ui.screen.view.Banner
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.IndexViewModel
import com.zxj.wanandroid.compose.viewmodel.UIEvent
import com.zxj.wanandroid.compose.widget.ArticleItem
import com.zxj.wanandroid.compose.widget.PagingLazyColumn
import com.zxj.wanandroid.compose.widget.handleLoadState
import kotlinx.coroutines.flow.flowOf


fun NavGraphBuilder.addHomeIndex(onBrowser: (String) -> Unit) {
    composable(Screen.HomeIndex.route) {
        IndexRoute(
            onBrowser = onBrowser,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun IndexRoute(
    modifier: Modifier = Modifier,
    onBrowser: (String) -> Unit,
    indexViewModel: IndexViewModel = hiltViewModel()
) {
    val uiState by indexViewModel.uiState.collectAsState()
    val pagingItems = indexViewModel.pageItems.collectAsLazyPagingItems()
    IndexScreen(
        bannerList = uiState.bannerList,
        modifier = modifier,
        onBrowser = onBrowser,
        onItemCollect = indexViewModel::dealZanAction,
        pagingItems = pagingItems,
    )
    LaunchedEffect(Unit) {
        indexViewModel.uiEvent.collect {
            when (it) {
                is UIEvent.ShowToast -> {
                    toast(it.msg)
                }
            }
        }
    }
}

@Composable
private fun IndexScreen(
    bannerList: List<BannerBean>,
    pagingItems: LazyPagingItems<Article>,
    modifier: Modifier = Modifier,
    onBrowser: (String) -> Unit,
    onItemCollect: (Boolean, Article) -> Unit
) {
    PagingLazyColumn(modifier = modifier, pagingItems = pagingItems) {
        // BannerList 点击
        if (bannerList.isNotEmpty()) {
            item(contentType = 0) {
                Banner(Modifier.fillMaxWidth(), bannerList = bannerList) {
                    onBrowser(it.url)
                }
            }
        }
        items(pagingItems, key = { it.id }) {
            ArticleItem(
                it!!,
                onItemZanClick = onItemCollect,
                onItemClick = { onBrowser(it.link) }
            )
        }
        handleLoadState(pagingItems)
    }

}


@Preview
@Composable
fun PreviewIndexPage() {
    WanAndroidTheme {
        IndexScreen(
            bannerList = emptyList(),
            pagingItems = flowOf(PagingData.empty<Article>()).collectAsLazyPagingItems(),
            onBrowser = {},
            onItemCollect = { _, _ -> }
        )
    }
}