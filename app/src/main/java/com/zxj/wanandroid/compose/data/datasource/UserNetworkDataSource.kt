package com.zxj.wanandroid.compose.data.datasource

import com.zxj.wanandroid.compose.net.UserAPI
import javax.inject.Inject

class UserNetworkDataSource @Inject constructor(
    private val userAPI: UserAPI
) {
    suspend fun register() {

    }

    suspend fun login() {

    }
}