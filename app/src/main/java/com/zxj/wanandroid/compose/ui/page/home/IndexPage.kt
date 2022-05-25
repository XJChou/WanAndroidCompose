package com.zxj.wanandroid.compose.ui.page.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Dimension
import coil.size.OriginalSize
import coil.size.Size
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.zxj.wanandroid.compose.data.BannerBean
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.IndexViewModel

@Composable
fun IndexPage() {
    val indexViewModel: IndexViewModel = viewModel()
    val uiState by indexViewModel.uiState.collectAsState()
    // 这里应该套一个 refresh-layout
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(WanAndroidTheme.colors.windowBackground)
    ) {

        // banner view
        val bannerList = uiState.bannerList
        if (bannerList != null) {
            item { Banner(bannerList) }
        }

        // 列表内容
        items(2) {
            Text(text = "items")
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
        Modifier.fillMaxWidth()
    ) { page ->
        val item = bannerList[page]
        Box(Modifier.fillMaxWidth()) {
            // 图片
            val imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(item.imagePath)
                .size(Size.ORIGINAL)
                .build()
            val painter = rememberAsyncImagePainter(imageRequest)
            Image(
                painter = painter,
                contentDescription = item.title,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
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