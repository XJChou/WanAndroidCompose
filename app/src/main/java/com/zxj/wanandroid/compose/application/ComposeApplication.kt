package com.zxj.wanandroid.compose.application

import android.app.Application
import android.widget.Toast
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

fun toast(title: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(ComposeApplication.application, title, duration).show()

fun toast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(ComposeApplication.application, stringRes, duration).show()