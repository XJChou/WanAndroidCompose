package com.zxj.wanandroid.compose.data.datasource

import com.zxj.wanandroid.compose.net.IndexAPI
import javax.inject.Inject

class IndexNetworkDataSource @Inject constructor(
    private val indexAPI: IndexAPI
) {
    suspend fun loadBannerList() = indexAPI.loadBanner()

    suspend fun loadArticleList(page: Int) = indexAPI.loadArticleList(page)

    suspend fun loadTopArticleList() = indexAPI.loadTopArticleList()
}