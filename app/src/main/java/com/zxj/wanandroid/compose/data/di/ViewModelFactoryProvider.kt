package com.zxj.wanandroid.compose.data.di

import com.zxj.wanandroid.compose.ui.home.wechat.WechatArticleDetailViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    fun wechatArticleDetailViewModel(): WechatArticleDetailViewModel.Factory

}
