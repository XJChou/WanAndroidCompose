package com.zxj.wanandroid.compose.ui.search

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.GetString
import com.zxj.wanandroid.compose.data.bean.HotSearchBean
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

@Composable
fun ColumnScope.HotSearchPage(hotSearchList: List<HotSearchBean>) {
    Text(
        text = GetString(id = R.string.hot_search),
        color = WanAndroidTheme.colors.colorAccent,
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    // TagLayout
}


