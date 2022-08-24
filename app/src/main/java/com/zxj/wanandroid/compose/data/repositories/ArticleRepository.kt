package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.bean.ArticleBean
import com.zxj.wanandroid.compose.data.bean.BannerBean
import com.zxj.wanandroid.compose.net.API
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path


/**
 * 文章的存储库
 */
interface ArticleRepository {

    val collectFlow: Flow<Pair<Int, Boolean>>

    suspend fun loadBannerList(): API<List<BannerBean>>

    suspend fun loadDataList(page: Int): API<ArticleBean>

    suspend fun loadSearchArticleList(page: Int, key: String): API<ArticleBean>

    suspend fun addCollectArticle(@Path("id") id: Int): API<String>

    suspend fun removeCollectArticle(@Path("id") id: Int): API<String>
}