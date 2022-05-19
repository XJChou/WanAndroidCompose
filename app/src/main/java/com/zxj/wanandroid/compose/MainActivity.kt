package com.zxj.wanandroid.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zxj.wanandroid.compose.ui.NavigationBar
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {

    val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanAndroidTheme(viewModel.theme) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = WanAndroidTheme.colors.windowBackground
                ) {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home() {
    var selectIndex by remember { mutableStateOf(0) }
    val viewModel: HomeViewModel = viewModel()
    Box(Modifier.fillMaxSize()) {
        // 上部分
        Column(Modifier.matchParentSize()) {
            // 菜单栏
            // 列表
        }

        // 底部导航

        NavigationBar(Modifier.align(Alignment.BottomStart), selectIndex) {
            selectIndex = it
            viewModel.theme = when (viewModel.theme) {
                WanAndroidTheme.Theme.Normal -> {
                    WanAndroidTheme.Theme.Night
                }
                else -> {
                    WanAndroidTheme.Theme.Normal
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WanAndroidTheme {
    }
}