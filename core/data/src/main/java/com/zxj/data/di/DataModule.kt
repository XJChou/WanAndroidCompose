package com.zxj.data.di

import com.zxj.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindsArticleRepository(
        articleRepository: RealArticleRepository
    ): ArticleRepository

    @Binds
    @Singleton
    fun bindsSearchRepository(
        searchRepository: RealSearchRepository
    ): SearchRepository

    @Binds
    @Singleton
    fun bindsUserRepository(
        userRepository: RealUserRepository
    ): UserRepository


}