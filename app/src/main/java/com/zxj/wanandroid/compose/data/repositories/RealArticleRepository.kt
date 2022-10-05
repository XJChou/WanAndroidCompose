package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.bean.*
import com.zxj.wanandroid.compose.data.datasource.ArticleNetworkDataSource
import com.zxj.wanandroid.compose.data.datasource.CollectNetworkDataSource
import com.zxj.wanandroid.compose.data.datasource.IndexLocalDataSource
import com.zxj.wanandroid.compose.net.API
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

/**
 * Repository职责
 *
 * 向应用的其余部分公开数据。
 *
 * 集中处理数据变化。
 *
 * 解决多个数据源之间的冲突。
 *
 * 对应用其余部分的数据源进行抽象化处理。
 *
 * 包含业务逻辑。
 */
class RealArticleRepository @Inject constructor(
    private val networkDataSource: ArticleNetworkDataSource,
    private val networkLocalSource: IndexLocalDataSource,
    private val collectNetworkDataSource: CollectNetworkDataSource
) : ArticleRepository {

    //    private val _collectEvent: Channel<Pair<Int, Boolean>> = Channel()
    private val _collectEvent = MutableSharedFlow<Pair<Int, Boolean>>(0)

    override val collectFlow: Flow<Pair<Int, Boolean>>
        get() = _collectEvent.asSharedFlow()

    /**
     * 提供Banner内容
     */
    override suspend fun loadBannerList() = networkDataSource.loadBanner()

    /**
     * 提供每一页内容
     */
    override suspend fun loadDataList(page: Int): API<ListData<Article>> {
        return if (page == 1) {
            coroutineScope {
                val topArticleListAsync = async { networkDataSource.loadTopArticleList() }
                val articleListAsync = async { networkDataSource.loadArticleList(page) }

                val topArticleListAwait = topArticleListAsync.await()
                val articleListAwait = articleListAsync.await()

                if (topArticleListAwait.isSuccess && articleListAwait.isSuccess) {
                    val targetArticleList = arrayListOf<Article>()

                    val topArticleList = topArticleListAwait.data
                    if (!topArticleList.isNullOrEmpty()) {
                        targetArticleList += topArticleList.also {
                            it.forEach { it.top = "1" }
                        }
                    }

                    val articleList = articleListAwait.data?.datas
                    if (!articleList.isNullOrEmpty()) {
                        targetArticleList += articleList
                    }

                    // 组合数据
                    val result = articleListAwait.data?.copy(datas = targetArticleList)

                    API(
                        topArticleListAwait.errorCode,
                        topArticleListAwait.errorMsg,
                        result
                    )
                } else if (articleListAwait.isSuccess) {
                    API(
                        topArticleListAwait.errorCode,
                        topArticleListAwait.errorMsg,
                        null
                    )
                } else {
                    articleListAwait
                }
            }
        } else {
            networkDataSource.loadArticleList(page)
        }
    }

    override suspend fun loadSearchArticleList(page: Int, key: String): API<ListData<Article>> {
        return networkDataSource.loadSearchArticleList(page, key)
    }

    override suspend fun loadCollectArticleList(page: Int): API<ListData<CollectionArticle>> {
        return collectNetworkDataSource.loadCollectList(page)
    }

    /**
     * 添加收藏相同内容
     */
    override suspend fun addCollectArticle(id: Int): API<String> {
        return collectNetworkDataSource.addCollectArticle(id).ifSuspendSuccess {
            _collectEvent.emit(Pair(id, true))
        }
    }

    /**
     * 删除收藏
     */
    override suspend fun removeCollectArticle(id: Int): API<String> {
        return collectNetworkDataSource.removeCollectArticle(id).ifSuspendSuccess {
            _collectEvent.emit(Pair(id, false))
        }
    }

    override suspend fun loadShareList(page: Int): API<ShareResponseBody> {
        return networkDataSource.loadShareList(page)
    }

    override suspend fun loadWechatChapters(): API<List<WXChapterBean>> {
        return networkDataSource.loadWechatChapters()
    }

    override suspend fun loadKnowledgeList(page: Int, cid: Int): API<ListData<Article>> {
        return networkDataSource.loadKnowledgeList(page, cid)
    }


}