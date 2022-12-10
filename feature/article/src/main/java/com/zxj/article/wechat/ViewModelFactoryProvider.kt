package com.zxj.article.wechat

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    fun wechatArticleDetailViewModel(): WechatArticleDetailViewModel.Factory

}
