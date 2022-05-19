package com.zxj.wanandroid.compose.application

import android.app.Application
import androidx.annotation.StringRes

class ComposeApplication : Application() {

    companion object {
        lateinit var application: Application
            private set
    }


    override fun onCreate() {
        super.onCreate()
        application = this
    }
}

fun getString(@StringRes id: Int): String = ComposeApplication.application.getString(id)
