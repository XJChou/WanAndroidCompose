package com.zxj.wanandroid.compose.data.datasource

import com.zxj.wanandroid.compose.data.bean.*
import com.zxj.wanandroid.compose.net.API
import retrofit2.http.*

interface ArticleNetworkDataSource {

    /**
     * 首页文章列表
     * @param page 页码
     */
    @GET("/article/list/{page}/json")
    suspend fun loadArticleList(@Path("page") page: Int): API<ListData<Article>>

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
    ): API<ListData<Article>>

    /**
     * 置顶
     * @param page 页码
     */
    @GET("/article/top/json")
    suspend fun loadTopArticleList(): API<List<Article>>

    /**
     * 首页banner
     */
    @GET("/banner/json")
    suspend fun loadBanner(): API<List<BannerBean>>


    /**
     * 自己的分享的文章列表
     * https://wanandroid.com/user/lg/private_articles/1/json
     * @param page 页码 从1开始
     */
    @GET("user/lg/private_articles/{page}/json")
    suspend fun loadShareList(@Path("page") page: Int): API<ShareResponseBody>

    /**
     * 分享文章
     * https://www.wanandroid.com/lg/user_article/add/json
     * @param map
     *      title: 文章标题
     *      link:  文章链接
     */
    @POST("lg/user_article/add/json")
    @FormUrlEncoded
    suspend fun shareArticle(@FieldMap map: MutableMap<String, Any>): API<Any>

    /**
     * 删除自己分享的文章
     * https://wanandroid.com/lg/user_article/delete/9475/json
     * @param id 文章id，拼接在链接上
     */
    @POST("lg/user_article/delete/{id}/json")
    suspend fun deleteShareArticle(@Path("id") id: Int): API<Any>


}