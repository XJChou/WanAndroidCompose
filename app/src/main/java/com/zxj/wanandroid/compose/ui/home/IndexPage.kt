package com.zxj.wanandroid.compose.ui.screen.home

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.data.bean.Article
import com.zxj.wanandroid.compose.data.bean.BannerBean
import com.zxj.wanandroid.compose.ui.screen.view.Banner
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.IndexViewModel
import com.zxj.wanandroid.compose.viewmodel.UIEvent
import com.zxj.wanandroid.compose.widget.ArticleItem
import com.zxj.wanandroid.compose.widget.PagingLazyColumn
import kotlinx.coroutines.flow.flowOf

@Composable
fun IndexPage(
    onBrowser: (String) -> Unit,
    modifier: Modifier = Modifier,
    indexViewModel: IndexViewModel = hiltViewModel()
) {
    val uiState by indexViewModel.uiState.collectAsState()
    val pagingItems = indexViewModel.pageItems.collectAsLazyPagingItems()
    IndexPage(
        modifier = modifier
            .fillMaxSize()
            .background(WanAndroidTheme.colors.windowBackground),
        bannerList = uiState.bannerList,
        pagingItems = pagingItems,
        onItemZan = { collect, data -> indexViewModel.dealZanAction(collect, data) },
        onBrowser = onBrowser
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

@VisibleForTesting
@Composable
private fun IndexPage(
    bannerList: List<BannerBean>,
    pagingItems: LazyPagingItems<Article>,
    modifier: Modifier = Modifier,
    onItemZan: (Boolean, Article) -> Unit = { _, _ -> },
    onBrowser: (String) -> Unit = {},
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
                onItemZanClick = onItemZan,
                onItemClick = { onBrowser(it.link) }
            )
        }
    }
}

@Preview
@Composable
fun PreviewIndexPage() {
    WanAndroidTheme {
        IndexPage(
            bannerList = emptyList(),
            pagingItems = flowOf(PagingData.empty<Article>()).collectAsLazyPagingItems(),
            onBrowser = {}
        )
    }
}