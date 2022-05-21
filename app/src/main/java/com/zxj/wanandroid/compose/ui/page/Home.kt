package com.zxj.wanandroid.compose.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.ui.ControlBean
import com.zxj.wanandroid.compose.ui.NavigationBar
import com.zxj.wanandroid.compose.ui.Toolbar
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.HomeViewModel

val TITLE = arrayOf(
    "玩Android",
    "广场",
    "公众号",
    "体系",
    "项目",
)

@Composable
fun Home() {
    val viewModel: HomeViewModel = viewModel()
    var selectIndex by remember { mutableStateOf(0) }
    Box(Modifier.fillMaxSize()) {
        // 上部分
        LazyColumn(Modifier.matchParentSize()) {
            // 列表
        }

        // 菜单栏
        Toolbar(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(),
            leftControl = arrayListOf(
                ControlBean(R.drawable.ic_menu_white_24dp) {
                    toast("菜单")
                }
            ),
            title = TITLE[selectIndex],
            titleColor = WanAndroidTheme.colors.itemTagTv,
            rightControl = arrayListOf(
                ControlBean(R.drawable.ic_search_white_24dp) {
                    toast("搜索")
                }
            )
        )

        // 底部导航
        NavigationBar(Modifier.align(Alignment.BottomStart), selectIndex) {
            selectIndex = it
        }
    }
}
