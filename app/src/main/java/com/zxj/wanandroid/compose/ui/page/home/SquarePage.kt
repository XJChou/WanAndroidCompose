package com.zxj.wanandroid.compose.ui.page.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

@Composable
fun SquarePage() {
    Text(
        text = "广场",
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun prev() {
    WanAndroidTheme {
        SquarePage()
    }
}