package com.zxj.wanandroid.compose.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.HorizontalPager
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

@Composable
fun IndexPage() {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(WanAndroidTheme.colors.windowBackground)
    ) {

        // viewpager
//        HorizontalPager()

        // 列表


    }
}

@Preview
@Composable
fun PreviewIndex() {
    WanAndroidTheme {
        IndexPage()
    }
}