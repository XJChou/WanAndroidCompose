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
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.data.bean.BannerBean
import com.zxj.wanandroid.compose.data.bean.Data
import com.zxj.wanandroid.compose.ui.screen.view.Banner
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.IndexViewModel
import com.zxj.wanandroid.compose.viewmodel.UIEvent
import com.zxj.wanandroid.compose.widget.ArticleItem
import com.zxj.wanandroid.compose.widget.NextState
import com.zxj.wanandroid.compose.widget.SmartLazyColumn
import com.zxj.wanandroid.compose.widget.rememberNextState

@Composable
fun IndexPage(
    onBrowser: (String) -> Unit,
    modifier: Modifier = Modifier,
    indexViewModel: IndexViewModel = hiltViewModel()
) {
    val uiState by indexViewModel.uiState.collectAsState()
    IndexPage(
        modifier = modifier
            .fillMaxSize()
            .background(WanAndroidTheme.colors.windowBackground),
        onRefresh = { indexViewModel.refresh() },
        isRefresh = uiState.refresh,
        onPageNext = { indexViewModel.nextPage() },
        nextPageState = uiState.nextState,
        bannerList = uiState.bannerList,
        articleList = uiState.articleList,
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
    onRefresh: () -> Unit,
    isRefresh: Boolean,
    onPageNext: () -> Unit,
    nextPageState: Int,
    modifier: Modifier = Modifier,
    bannerList: List<BannerBean> = emptyList(),
    articleList: List<Data> = emptyList(),
    onItemZan: (Boolean, Data) -> Unit = { _, _ -> },
    onBrowser: (String) -> Unit = {},
) {
    // 刷新布局
    SmartLazyColumn(
        modifier = modifier,
        onRefresh = onRefresh,
        onPageNext = onPageNext,
        refreshState = rememberSwipeRefreshState(isRefresh).also { it.isRefreshing = isRefresh },
        nextState = rememberNextState().also { it.state = nextPageState }
    ) {

        // BannerList 点击
        if (bannerList.isNotEmpty()) {
            item(contentType = 0) {
                Banner(Modifier.fillMaxWidth(), bannerList = bannerList) {
                    onBrowser(it.url)
                }
            }
        }

        // 列表内容
        items(articleList.size, contentType = { 1 }) {
            ArticleItem(
                articleList[it],
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
            onRefresh = { },
            isRefresh = false,
            onPageNext = { },
            nextPageState = NextState.STATE_NONE,
            onBrowser = {}
        )
    }
}