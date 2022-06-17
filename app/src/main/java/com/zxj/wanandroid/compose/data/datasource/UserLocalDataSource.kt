package com.zxj.wanandroid.compose.data.datasource

import com.zxj.wanandroid.compose.data.bean.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserLocalDataSource @Inject constructor() {

    fun setLoginUser(user: User?) {
        // 设置DataSource
    }

    fun getLoginUser(): Flow<User?> {
        // 从DataSource取
        return flow { null }
    }
}