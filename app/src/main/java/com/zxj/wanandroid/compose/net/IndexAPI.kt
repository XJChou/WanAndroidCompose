package com.zxj.wanandroid.compose.net

import com.zxj.wanandroid.compose.data.ArticleBean
import com.zxj.wanandroid.compose.data.BannerBean
import retrofit2.http.GET
import retrofit2.http.Path

interface IndexAPI {

    /**
     * 首页文章列表
     * @param page 页码
     */
    @GET("/article/list/{page}/json")
    suspend fun loadArticleList(@Path("page") page: Int): API<ArticleBean>


    /**
     * 首页banner
     */
    @GET("/banner/json")
    suspend fun loadBanner(): API<List<BannerBean>>


}