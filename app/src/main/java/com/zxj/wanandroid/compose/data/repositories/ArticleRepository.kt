package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.bean.ArticleBean
import com.zxj.wanandroid.compose.data.bean.BannerBean
import com.zxj.wanandroid.compose.net.API


/**
 * 文章的存储库
 */
interface ArticleRepository {

    suspend fun loadBannerList(): API<List<BannerBean>>

    suspend fun loadDataList(page: Int): API<ArticleBean>
}