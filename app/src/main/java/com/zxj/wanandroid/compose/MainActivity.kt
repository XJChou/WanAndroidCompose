package com.zxj.wanandroid.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import com.zxj.wanandroid.compose.utils.MyLog

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WanAndroidTheme(WanAndroidTheme.theme) {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(Color.Transparent)
                systemUiController.setNavigationBarColor(Color.Black)
//                AnimateStatusColor(window)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().navigationBarsPadding(),
                    color = WanAndroidTheme.colors.windowBackground
                ) {
                    MyLog.e("viewModel.theme=${WanAndroidTheme.theme}")
                    MainNavigation()
                }
            }
        }
    }
}

