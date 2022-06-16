package com.zxj.wanandroid.compose.data.datasource

import com.zxj.wanandroid.compose.data.bean.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserLocalDataSource @Inject constructor() {
    fun getLoginUser(): Flow<User?> {
        return flow { null }
    }
}