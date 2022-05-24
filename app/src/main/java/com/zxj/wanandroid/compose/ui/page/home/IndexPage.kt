package com.zxj.wanandroid.compose.ui.page.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.zxj.wanandroid.compose.data.BannerBean
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.IndexViewModel

@Composable
fun IndexPage() {
    val indexViewModel: IndexViewModel = viewModel()
    val uiState by indexViewModel.uiState.collectAsState()
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(WanAndroidTheme.colors.windowBackground)
    ) {

        val bannerList = uiState.bannerList
        if (bannerList != null) {
            item { Banner(bannerList) }
        }

        items(2) {
            Text(text = "items")
        }
        item {
            Text(
                text = "Footer",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 50.dp),
                textAlign = TextAlign.Center
            )
        }
        // viewpager
//        HorizontalPager()

        // 列表


    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
fun Banner(bannerList: List<BannerBean>) {
    HorizontalPager(
        bannerList.size,
        Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) { page ->
        val item = bannerList[page]
        Box(Modifier.fillMaxSize()) {
            // 图片
            Image(
                painter = rememberAsyncImagePainter(item.imagePath),
                contentDescription = item.title
            )
            // 文字
            Text(
                text = item.title,
                Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
            )

        }
    }
}

@Preview
@Composable
fun PreviewIndex() {
    WanAndroidTheme {
        IndexPage()
    }
}