package com.zxj.wanandroid.compose.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.zxj.wanandroid.compose.data.bean.WXChapterBean
import com.zxj.wanandroid.compose.ui.home.wechat.WechatArticleDetailRoute
import com.zxj.wanandroid.compose.ui.home.wechat.WechatViewModel
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.widget.MultipleStatus
import kotlinx.coroutines.launch

@Composable
fun WechatRoute(
    onBrowser: (String) -> Unit,
    modifier: Modifier = Modifier,
    wechatViewModel: WechatViewModel = hiltViewModel()
) {
    val state by wechatViewModel.uiState.collectAsState()
    MultipleStatus(
        modifier = modifier,
        status = state,
        retry = wechatViewModel::loadData
    ) {
        WechatScreen(
            modifier = Modifier.fillMaxSize(),
            chapters = getData(),
            onBrowser = onBrowser
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun WechatScreen(
    modifier: Modifier,
    chapters: List<WXChapterBean>,
    onBrowser: (String) -> Unit
) {
    val pagerState = rememberPagerState()
    val selectedTab by remember { derivedStateOf { pagerState.currentPage } }
    val scope = rememberCoroutineScope()

    Column(modifier = modifier) {
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            backgroundColor = WanAndroidTheme.colors.colorPrimary,
            contentColor = Color.White,
            edgePadding = 0.dp
        ) {
            chapters.forEachIndexed { index, wxChapterBean ->
                Tab(
                    selected = selectedTab == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index, 0f)
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(20.dp, 15.dp),
                        text = wxChapterBean.name
                    )
                }
            }
        }

        // ViewPager
        HorizontalPager(
            count = chapters.size,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState,
            key = { chapters[it].id },
        ) {
            WechatArticleDetailRoute(
                modifier = Modifier.fillMaxSize(),
                chapter = chapters[it],
                onBrowser = onBrowser
            )
        }
    }
}


@Preview
@Composable
fun PreviewPublicPage() {
    WanAndroidTheme {
        WechatScreen(
            modifier = Modifier.fillMaxSize(),
            listOf(
                WXChapterBean(listOf(), 0, 0, "鸿洋", 0, 0, false, 0),
                WXChapterBean(listOf(), 0, 0, "郭霖", 0, 0, false, 0),
                WXChapterBean(listOf(), 0, 0, "玉刚说", 0, 0, false, 0),
                WXChapterBean(listOf(), 0, 0, "陈翔魔音", 0, 0, false, 0),
            ),
            onBrowser = {}
        )
    }
}