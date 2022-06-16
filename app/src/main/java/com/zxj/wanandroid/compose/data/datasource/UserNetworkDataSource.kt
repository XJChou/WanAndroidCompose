package com.zxj.wanandroid.compose.data.datasource

import com.zxj.wanandroid.compose.data.bean.User
import com.zxj.wanandroid.compose.net.API
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

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

    @GET("/user/logout/json")
    suspend fun logout(): API<String>
}