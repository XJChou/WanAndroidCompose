package com.zxj.wanandroid.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.zxj.wanandroid.compose.application.AnimateStatusColor
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.utils.MyLog

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanAndroidTheme(WanAndroidTheme.theme) {
                AnimateStatusColor(window)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = WanAndroidTheme.colors.windowBackground
                ) {
                    MyLog.e("viewModel.theme=${WanAndroidTheme.theme}")
                    MainNavigation()
                }
            }
        }
    }
}

