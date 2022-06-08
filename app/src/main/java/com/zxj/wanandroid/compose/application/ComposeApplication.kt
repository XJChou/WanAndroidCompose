package com.zxj.wanandroid.compose.application

import android.app.Application
import android.view.Window
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.toArgb
import com.zxj.wanandroid.compose.BuildConfig
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.utils.MyLog
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ComposeApplication : Application() {

    companion object {
        lateinit var application: Application
            private set
    }


    override fun onCreate() {
        super.onCreate()
        application = this
        MyLog.init(BuildConfig.DEBUG)
    }
}

fun getString(@StringRes id: Int): String = ComposeApplication.application.getString(id)

fun toast(title: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(ComposeApplication.application, title, duration).show()

fun toast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(ComposeApplication.application, stringRes, duration).show()

@Composable
fun AnimateStatusColor(window: Window) {
    val targetColor by animateColorAsState(
        WanAndroidTheme
            .colors
            .colorPrimary
            .convert(ColorSpaces.LinearSrgb)
    )
    window.statusBarColor = targetColor.toArgb()
}