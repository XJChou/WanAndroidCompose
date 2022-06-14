package com.zxj.wanandroid.compose.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.zxj.wanandroid.compose.ui.screen.home.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePager(count:Int,pagerState: PagerState,modifier: Modifier) {

    HorizontalPager(count = count,state = pagerState,modifier = modifier) {
        when (it) {
            0 -> IndexPage()
            1 -> SquarePage()
            2 -> PublicPage()
            3 -> SystemPage()
            4 -> ProjectPage()
        }
    }
}