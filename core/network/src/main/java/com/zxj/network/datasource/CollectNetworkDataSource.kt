package com.zxj.network.datasource

import com.zxj.model.API
import com.zxj.model.CollectionBean
import com.zxj.model.ListData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CollectNetworkDataSource {
    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     * @param id article id
     */
    @POST("lg/collect/{id}/json")
    suspend fun addCollectArticle(@Path("id") id: String): API<String>

    /**
     * 文章列表中取消收藏文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     * @param id
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun removeCollectArticle(@Path("id") id: String): API<String>

    /**
     *  获取收藏列表
     *  http://www.wanandroid.com/lg/collect/list/0/json
     *  @param page
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun loadCollectList(@Path("page") page: Int): API<ListData<CollectionBean>>
}