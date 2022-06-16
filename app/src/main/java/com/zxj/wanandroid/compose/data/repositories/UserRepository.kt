package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.bean.User
import kotlinx.coroutines.flow.Flow


/**
 * 用户数据存储库
 */
interface UserRepository {

    fun getLoginUser(): Flow<User?>

}