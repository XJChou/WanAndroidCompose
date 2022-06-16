package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.bean.User
import com.zxj.wanandroid.compose.data.datasource.UserLocalDataSource
import com.zxj.wanandroid.compose.data.datasource.UserNetworkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealUserRepository @Inject constructor(
    private val userNetworkDataSource: UserNetworkDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    /**
     * 从本地获取数据
     */
    override fun getLoginUser(): Flow<User?> = userLocalDataSource.getLoginUser()
}