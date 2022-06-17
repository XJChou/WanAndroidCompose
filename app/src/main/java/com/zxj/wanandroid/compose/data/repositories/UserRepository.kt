package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.bean.User
import com.zxj.wanandroid.compose.net.API
import kotlinx.coroutines.flow.Flow


/**
 * 用户数据存储库
 */
interface UserRepository {

    /**
     * 获取当前登录用户
     */
    val loginUser: Flow<User?>

    suspend fun register(
        username: String,
        password: String,
        confirmPassword: String
    ): API<User>

    suspend fun login(username: String, password: String): API<User>

    suspend fun loginOut(): API<String>

}