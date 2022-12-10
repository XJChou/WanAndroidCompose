package com.zxj.network.di

import com.zxj.network.APIFactory
import com.zxj.network.datasource.ArticleNetworkDataSource
import com.zxj.network.datasource.CollectNetworkDataSource
import com.zxj.network.datasource.SearchNetworkDataSource
import com.zxj.network.datasource.UserNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Provides
    @Singleton
    fun providesArticleNetworkDataSource(apiFactory: APIFactory): ArticleNetworkDataSource =
        apiFactory.get()

    @Provides
    @Singleton
    fun providesCollectNetworkDataSource(apiFactory: APIFactory): CollectNetworkDataSource =
        apiFactory.get()

    @Provides
    @Singleton
    fun providesSearchNetworkDataSource(apiFactory: APIFactory): SearchNetworkDataSource =
        apiFactory.get()

    @Provides
    @Singleton
    fun providesUserNetworkDataSource(apiFactory: APIFactory): UserNetworkDataSource =
        apiFactory.get()
}