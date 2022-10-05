package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.bean.*
import com.zxj.wanandroid.compose.net.API
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path


/**
 * 文章的存储库
 */
interface ArticleRepository {

    val collectFlow: Flow<Pair<Int, Boolean>>

    suspend fun loadBannerList(): API<List<BannerBean>>

    suspend fun loadDataList(page: Int): API<ListData<Article>>

    suspend fun loadSearchArticleList(page: Int, key: String): API<ListData<Article>>

    suspend fun loadCollectArticleList(page: Int): API<ListData<CollectionArticle>>

    suspend fun addCollectArticle(@Path("id") id: Int): API<String>

    suspend fun removeCollectArticle(@Path("id") id: Int): API<String>

    suspend fun loadShareList(page: Int): API<ShareResponseBody>

    /**
     * 查作者列表
     */
    suspend fun loadWechatChapters(): API<List<WXChapterBean>>

    suspend fun loadKnowledgeList(page: Int, cid: Int): API<ListData<Article>>

}