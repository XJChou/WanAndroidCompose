package com.zxj.wanandroid.compose.ui.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun PublicPage() {

    Text(
        text = "公众号",
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}