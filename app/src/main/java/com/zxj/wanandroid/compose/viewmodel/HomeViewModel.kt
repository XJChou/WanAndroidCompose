package com.zxj.wanandroid.compose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

class HomeViewModel : ViewModel() {

    var theme by mutableStateOf(WanAndroidTheme.Theme.Normal)

}