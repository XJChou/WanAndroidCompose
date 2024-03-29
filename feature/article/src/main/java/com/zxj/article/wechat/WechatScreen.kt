package com.zxj.article.wechat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.WechatChapterBean
import com.zxj.ui.MultipleStatus
import kotlinx.coroutines.launch

@Composable
fun WechatRoute(
    modifier: Modifier = Modifier,
    wechatViewModel: WechatViewModel = hiltViewModel(),
    navigateToBrowser: (String) -> Unit
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
            onBrowser = navigateToBrowser
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun WechatScreen(
    modifier: Modifier,
    chapters: List<WechatChapterBean>,
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
                            pagerState.scrollToPage(index, 0f)
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
