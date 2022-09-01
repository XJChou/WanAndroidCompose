package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.bean.ListData
import com.zxj.wanandroid.compose.data.bean.User
import com.zxj.wanandroid.compose.data.bean.UserScoreBean
import com.zxj.wanandroid.compose.net.API
import kotlinx.coroutines.flow.Flow


/**
 * 用户数据存储库
 */
interface UserRepository {

    val isLogin: Flow<Boolean>

    /**
     * 获取当前登录用户
     */
    val user: Flow<User>

    suspend fun register(
        username: String,
        password: String,
        confirmPassword: String
    ): API<User>

    suspend fun signIn(username: String, password: String): API<User>

    suspend fun userInfo(): API<String>

    suspend fun signOut(): API<String>

    suspend fun loadUserScoreList(page: Int): API<ListData<UserScoreBean>>
}