package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.bean.ArticleBean
import com.zxj.wanandroid.compose.data.bean.Data
import com.zxj.wanandroid.compose.data.datasource.IndexLocalDataSource
import com.zxj.wanandroid.compose.data.datasource.IndexNetworkDataSource
import com.zxj.wanandroid.compose.net.API
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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
class IndexRepository @Inject constructor(
    // 网络数据源
    private val networkDataSource: IndexNetworkDataSource,
    // 数据库数据源
    private val networkLocalSource: IndexLocalDataSource
) {

    /**
     * 提供Banner内容
     */
    suspend fun loadBannerList() = networkDataSource.loadBannerList()

    /**
     * 提供每一页内容
     */
    suspend fun loadDataList(page: Int): API<ArticleBean> {
        return if (page == 1) {
            coroutineScope {
                val topArticleListAsync = async { networkDataSource.loadTopArticleList() }
                val articleListAsync = async { networkDataSource.loadArticleList(page) }

                val topArticleListAwait = topArticleListAsync.await()
                val articleListAwait = articleListAsync.await()

                if (topArticleListAwait.isSuccess && articleListAwait.isSuccess) {
                    val targetArticleList = arrayListOf<Data>()

                    val topArticleList = topArticleListAwait.data
                    if (!topArticleList.isNullOrEmpty()) {
                        targetArticleList.addAll(topArticleList.map { it.copy(top = "1") })
                    }

                    val articleList = articleListAwait.data?.datas
                    if (!articleList.isNullOrEmpty()) {
                        targetArticleList.addAll(articleList)
                    }

                    // 组合数据
                    val result = articleListAwait.data?.copy(
                        topCount = topArticleList?.size ?: 0,
                        datas = targetArticleList
                    )

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

}