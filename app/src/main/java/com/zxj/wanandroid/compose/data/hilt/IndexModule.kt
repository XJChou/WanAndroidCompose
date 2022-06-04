package com.zxj.wanandroid.compose.data.hilt

import com.zxj.wanandroid.compose.net.APIFactory
import com.zxj.wanandroid.compose.net.IndexAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class IndexModule {

    // @Binds - normal class
    // 使用场景：您无法通过构造函数注入它，而应向 Hilt 提供绑定信息，方法是在 Hilt 模块内创建一个带有 @Binds 注释的抽象函数。
    // 使用方法：https://developer.android.com/training/dependency-injection/hilt-android#inject-interfaces

    // @Provider - object class
    // 使用场景：接口不是无法通过构造函数注入类型的唯一一种情况。如果某个类不归您所有（因为它来自外部库，如 Retrofit、OkHttpClient 或 Room 数据库等类），或者必须使用构建器模式创建实例，也无法通过构造函数注入。
    // 使用方法：https://developer.android.com/training/dependency-injection/hilt-android#inject-provides

    // @Provider - 为同一类型提供多个绑定
    // https://developer.android.com/training/dependency-injection/hilt-android#multiple-bindings
    // 使用方法：

    //    @Singleton
    @Provides
    fun provideIndexApi(): IndexAPI = APIFactory.get()
}