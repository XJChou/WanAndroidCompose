package com.zxj.wanandroid.compose.ui.page.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SystemPage() {

    Text(
        text = "体系",
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}