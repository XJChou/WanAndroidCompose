package com.zxj.wanandroid.compose.data.di

import com.zxj.wanandroid.compose.net.APIFactory
import com.zxj.wanandroid.compose.net.UserAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
class UserModule {

    @Provides
    fun provideUserApi(): UserAPI = APIFactory.get()
}