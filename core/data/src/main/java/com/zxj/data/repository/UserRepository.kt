package com.zxj.data.repository

import com.zxj.model.*
import kotlinx.coroutines.flow.Flow


/**
 * 用户数据存储库
 */
interface UserRepository {

    val isLogin: Flow<Boolean>

    /**
     * 获取当前登录用户
     */
    val user: Flow<UserBean>

    /**
     * 用户信息
     */
    val userInfo: Flow<RankBean?>

    suspend fun register(
        username: String,
        password: String,
        confirmPassword: String
    ): API<UserBean>

    suspend fun signIn(username: String, password: String): API<UserBean>

    suspend fun loadUserInfo(): API<RankBean>

    suspend fun signOut(): API<String>

    suspend fun loadUserScoreList(page: Int): API<ListData<UserScoreBean>>

    suspend fun loadRankList(page: Int): API<ListData<RankBean>>
}