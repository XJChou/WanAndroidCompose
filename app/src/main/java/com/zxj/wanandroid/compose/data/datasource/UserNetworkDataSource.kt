package com.zxj.wanandroid.compose.data.datasource

import com.zxj.wanandroid.compose.data.bean.ListData
import com.zxj.wanandroid.compose.data.bean.User
import com.zxj.wanandroid.compose.data.bean.UserScoreBean
import com.zxj.wanandroid.compose.net.API
import retrofit2.http.*

interface UserNetworkDataSource {
    @POST("/user/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") confirmPassword: String
    ): API<User>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): API<User>

    /**
     * 获取个人积分，需要登录后访问
     * https://www.wanandroid.com/lg/coin/userinfo/json
     */
    @GET("/lg/coin/userinfo/json")
    suspend fun userInfo(): API<String>

    /**
     * 获取个人积分列表，需要登录后访问
     * https://www.wanandroid.com//lg/coin/list/1/json
     * @param page 页码 从1开始
     */
    @GET("/lg/coin/list/{page}/json")
    suspend fun loadUserScoreList(@Path("page") page: Int): API<ListData<UserScoreBean>>

    @GET("/user/logout/json")
    suspend fun logout(): API<String>
}