package com.zxj.wanandroid.compose.data.di

import com.zxj.wanandroid.compose.data.repositories.ArticleRepository
import com.zxj.wanandroid.compose.data.repositories.RealArticleRepository
import com.zxj.wanandroid.compose.data.repositories.RealUserRepository
import com.zxj.wanandroid.compose.data.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsUserRepository(userRepository: RealUserRepository): UserRepository

    @Binds
    @Singleton
    fun bindsArticleRepository(articleRepository: RealArticleRepository): ArticleRepository
}