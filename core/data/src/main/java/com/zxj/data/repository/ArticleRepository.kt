package com.zxj.data.repository

import com.zxj.model.*
import kotlinx.coroutines.flow.Flow

/**
 * 文章的存储库
 */
interface ArticleRepository {

    val collectFlow: Flow<Pair<String, Boolean>>

    suspend fun loadBannerList(): API<List<BannerBean>>

    suspend fun loadDataList(page: Int): API<ListData<ArticleBean>>

    suspend fun loadSearchArticleList(page: Int, key: String): API<ListData<ArticleBean>>

    suspend fun loadCollectArticleList(page: Int): API<ListData<CollectionBean>>

    suspend fun addCollectArticle(id: String): API<String>

    suspend fun removeCollectArticle(id: String): API<String>

    suspend fun loadShareList(page: Int): API<MineShareBean>

    /**
     * 查作者列表
     */
    suspend fun loadWechatChapters(): API<List<WechatChapterBean>>

    suspend fun loadKnowledgeList(page: Int, cid: Int): API<ListData<ArticleBean>>


    suspend fun loadProjectTree(): API<List<ProjectTreeBean>>

    suspend fun loadProjectList(page: Int, cid: Int): API<ListData<ArticleBean>>

}