package com.zxj.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.google.accompanist.pager.*
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.BannerBean
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * Banner
 *
 * 外部需要做的东西
 * 1. 每一Page如何显示
 *
 * 内部需要做的东西
 * 1. 自动轮播
 * 2. 索引器显示
 */
@Composable
@OptIn(ExperimentalPagerApi::class)
fun Banner(
    modifier: Modifier,
    bannerList: List<BannerBean>,
    onBannerItem: (BannerBean) -> Unit,
) {
    Box(modifier) {
        var pageDown by remember { mutableStateOf(false) }
        val startIndex = Int.MAX_VALUE / 2
        val state = rememberPagerState(startIndex)

        HorizontalPager(
            Int.MAX_VALUE,
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(state.currentPage) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent(PointerEventPass.Initial)
                            val dragEvent = event.changes.first()
                            when {
                                dragEvent.changedToDownIgnoreConsumed() -> {
                                    pageDown = true
                                }
                                dragEvent.changedToUpIgnoreConsumed() -> {
                                    pageDown = false
                                }
                            }
                        }
                    }
                },
            state,
        ) { index ->
            val page = (index - startIndex).floorMod(bannerList.size)
            BannerPage(onBannerItem, bannerList[page] )
        }

        // step2: 启动一个协程，用于自动轮播下一个
        StartInfiniteLoop(state, pageDown)

        // step3: Text + Indicators
        if (bannerList.isNotEmpty()) {
            BannerIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
                state = state,
                bannerList = bannerList
            )
        }
    }
}

private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

@Composable
private fun BannerPage(
    onBannerItem: (BannerBean) -> Unit,
    item: BannerBean
) {
    val context = LocalContext.current
    val request = remember(item.imagePath) {
        ImageRequest.Builder(context)
            .data(item.imagePath)
            .size(Size.ORIGINAL)
            .build()
    }
    AsyncImage(
        model = request,
        contentDescription = item.title,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onBannerItem(item)
            }
    )
}

/**
 * 开始无限滑动协程处理
 * @param state 主要控制Pager控件自动滚动
 * @param pagerDown 主要检测Pager控件是不是被按压
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun StartInfiniteLoop(state: PagerState, pagerDown: Boolean) {
    LaunchedEffect(state, pagerDown) {
        if (!pagerDown) {
            while (isActive) {
                delay(3000L)
                state.animateScrollToPage(state.currentPage + 1, 0f)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerIndicator(modifier: Modifier, state: PagerState, bannerList: List<BannerBean>) {
    Row(
        modifier = modifier
            .background(Color(0x44AAAAAA))
            .padding(13.dp, 6.dp)
    ) {
        Text(
            text = bannerList[state.currentPage % bannerList.size].title,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = WanAndroidTheme.colors.colorTitleBg,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        HorizontalPagerIndicator(
            state,
            modifier = Modifier.align(Alignment.CenterVertically),
            pageCount = bannerList.size,
            pageIndexMapping = { if (bannerList.isEmpty()) 0 else state.currentPage % bannerList.size },
            activeColor = Color.White,
            indicatorWidth = 6.dp,
            indicatorHeight = 6.dp,
            spacing = 4.dp
        )
    }
}