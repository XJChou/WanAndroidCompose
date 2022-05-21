package com.zxj.wanandroid.compose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.getString
import com.zxj.wanandroid.compose.ui.NavigationItemBean
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

class HomeViewModel : ViewModel() {

    var theme by mutableStateOf(WanAndroidTheme.Theme.Normal)

    val navigationItems = arrayOf(
        NavigationItemBean(R.drawable.ic_home_black_24dp, R.string.navigation_text_home),
        NavigationItemBean(R.drawable.ic_square_black_24dp, R.string.navigation_text_square),
        NavigationItemBean(R.drawable.ic_wechat_black_24dp, R.string.navigation_text_public),
        NavigationItemBean(R.drawable.ic_apps_black_24dp, R.string.navigation_text_system),
        NavigationItemBean(R.drawable.ic_project_black_24dp, R.string.navigation_text_project),
    )

    val TITLE = arrayOf(
        getString(R.string.toolbar_text_home),
        getString(R.string.toolbar_text_square),
        getString(R.string.toolbar_text_public),
        getString(R.string.toolbar_text_system),
        getString(R.string.toolbar_text_project)
    )
}