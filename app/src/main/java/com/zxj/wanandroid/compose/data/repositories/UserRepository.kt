package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.datasource.UserLocalDataSource
import com.zxj.wanandroid.compose.data.datasource.UserNetworkDataSource
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userNetworkDataSource: UserNetworkDataSource,
    private val userLocalDataSource: UserLocalDataSource
) {
}