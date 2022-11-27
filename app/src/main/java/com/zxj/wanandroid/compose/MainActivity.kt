package com.zxj.wanandroid.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.navigation.WanAndroidNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 声明自身管理WindowInsets
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // 当前的主题
            WanAndroidTheme(WanAndroidTheme.theme) {

                // 窗体设置
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(Color.Transparent) // 状态栏设置为透明
                systemUiController.setNavigationBarColor(Color.Black)

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    color = WanAndroidTheme.colors.windowBackground
                ) {
                    WanAndroidNavHost(
                        navController = rememberNavController(),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

