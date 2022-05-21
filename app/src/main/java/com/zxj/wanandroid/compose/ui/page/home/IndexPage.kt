package com.zxj.wanandroid.compose.ui.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

@Composable
fun IndexPage() {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(WanAndroidTheme.colors.windowBackground)
    ) {
        item {
            Text(
                text = "Header",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 50.dp),
                textAlign = TextAlign.Center
            )
        }
        items(2){
            Text(text = "items")
        }
        item{
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

@Preview
@Composable
fun PreviewIndex() {
    WanAndroidTheme {
        IndexPage()
    }
}