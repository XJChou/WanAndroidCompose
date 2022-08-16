package com.zxj.wanandroid.compose.data.datasource

import com.zxj.wanandroid.compose.data.bean.ArticleBean
import com.zxj.wanandroid.compose.data.bean.BannerBean
import com.zxj.wanandroid.compose.data.bean.Data
import com.zxj.wanandroid.compose.net.API
import retrofit2.http.*

interface ArticleNetworkDataSource {

    /**
     * 首页文章列表
     * @param page 页码
     */
    @GET("/article/list/{page}/json")
    suspend fun loadArticleList(@Path("page") page: Int): API<ArticleBean>

    /**
     * 加载关键字相关的文章列表
     * @param page 页码
     * @param key 关键字
     */
    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    suspend fun loadSearchArticleList(
        @Path("page") page: Int,
        @Field("k") key: String
    ): API<ArticleBean>

    /**
     * 置顶
     * @param page 页码
     */
    @GET("/article/top/json")
    suspend fun loadTopArticleList(): API<List<Data>>

    /**
     * 首页banner
     */
    @GET("/banner/json")
    suspend fun loadBanner(): API<List<BannerBean>>


}