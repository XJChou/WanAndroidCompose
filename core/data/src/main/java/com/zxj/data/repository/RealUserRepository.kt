package com.zxj.data.repository

import com.zxj.datastore.CookiesLocalDataSource
import com.zxj.datastore.UserLocalDataSource
import com.zxj.model.*
import com.zxj.network.datasource.UserNetworkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealUserRepository @Inject constructor(
    private val userNetworkDataSource: UserNetworkDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val cookiesLocalDataSource: CookiesLocalDataSource
) : UserRepository {

    override val isLogin: Flow<Boolean>
        get() = cookiesLocalDataSource.isLogin

    override val user: Flow<UserBean>
        get() = userLocalDataSource.user
    override val userInfo: Flow<RankBean?>
        get() = userLocalDataSource.userInfo

    private suspend fun updateUser(user: UserBean) {
        userLocalDataSource.updateUser(user)
    }

    override suspend fun register(
        username: String,
        password: String,
        confirmPassword: String
    ): API<UserBean> {
        return userNetworkDataSource.register(username, password, confirmPassword)
            .ifSuspendSuccess {
                updateUser(it!!)
            }
    }

    override suspend fun signIn(username: String, password: String): API<UserBean> {
        return userNetworkDataSource.login(username, password).ifSuspendSuccess {
            updateUser(it!!)
        }
    }

    override suspend fun loadUserInfo(): API<RankBean> {
        return userNetworkDataSource.userInfo().ifSuspendSuccess {
            userLocalDataSource.saveUserInfo(it)
        }
    }

    override suspend fun signOut(): API<String> {
        return userNetworkDataSource.logout().ifSuspendSuccess {
            cookiesLocalDataSource.clearToken()
        }
    }

    override suspend fun loadUserScoreList(page: Int): API<ListData<UserScoreBean>> {
        return userNetworkDataSource.loadUserScoreList(page)
    }

    override suspend fun loadRankList(page: Int): API<ListData<RankBean>> {
        return userNetworkDataSource.loadRankList(page + 1)
    }
}