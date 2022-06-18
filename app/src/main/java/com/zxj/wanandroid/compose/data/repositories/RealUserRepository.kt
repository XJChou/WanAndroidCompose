package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.bean.User
import com.zxj.wanandroid.compose.data.datasource.UserLocalDataSource
import com.zxj.wanandroid.compose.data.datasource.UserNetworkDataSource
import com.zxj.wanandroid.compose.net.API
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealUserRepository @Inject constructor(
    private val userNetworkDataSource: UserNetworkDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override val user: Flow<User>
        get() = userLocalDataSource.user

    private suspend fun updateUser(user: User) {
        userLocalDataSource.updateUser(user)
    }

    override suspend fun register(
        username: String,
        password: String,
        confirmPassword: String
    ): API<User> {
        return userNetworkDataSource.register(username, password, confirmPassword)
            .onSuspendSuccess {
                updateUser(it!!)
            }
    }

    override suspend fun login(username: String, password: String): API<User> {
        return userNetworkDataSource.login(username, password).onSuspendSuccess {
            updateUser(it!!)
        }
    }

    override suspend fun loginOut(): API<String> {
        return userNetworkDataSource.logout().onSuspendSuccess {
            userLocalDataSource.clearToken()
        }
    }

}