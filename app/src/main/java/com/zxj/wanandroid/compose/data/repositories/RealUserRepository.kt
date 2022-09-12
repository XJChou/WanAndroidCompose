package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.HttpConstant
import com.zxj.wanandroid.compose.data.bean.*
import com.zxj.wanandroid.compose.data.datasource.UserLocalDataSource
import com.zxj.wanandroid.compose.data.datasource.UserNetworkDataSource
import com.zxj.wanandroid.compose.net.API
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealUserRepository @Inject constructor(
    private val userNetworkDataSource: UserNetworkDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override val isLogin: Flow<Boolean>
        get() = HttpConstant.isLogin

    override val user: Flow<User>
        get() = userLocalDataSource.user
    override val userInfo: Flow<UserInfoBody?>
        get() = userLocalDataSource.userInfo

    private suspend fun updateUser(user: User) {
        userLocalDataSource.updateUser(user)
    }

    override suspend fun register(
        username: String,
        password: String,
        confirmPassword: String
    ): API<User> {
        return userNetworkDataSource.register(username, password, confirmPassword)
            .ifSuspendSuccess {
                updateUser(it!!)
            }
    }

    override suspend fun signIn(username: String, password: String): API<User> {
        return userNetworkDataSource.login(username, password).ifSuspendSuccess {
            updateUser(it!!)
        }
    }

    override suspend fun loadUserInfo(): API<UserInfoBody> {
        return userNetworkDataSource.userInfo().ifSuspendSuccess {
            userLocalDataSource.saveUserInfo(it)
        }
    }

    override suspend fun signOut(): API<String> {
        return userNetworkDataSource.logout().ifSuspendSuccess {
            HttpConstant.clearToken()
        }
    }

    override suspend fun loadUserScoreList(page: Int): API<ListData<UserScoreBean>> {
        return userNetworkDataSource.loadUserScoreList(page)
    }

    override suspend fun loadRankList(page: Int): API<ListData<CoinInfoBean>> {
        return userNetworkDataSource.loadRankList(page + 1)
    }
}