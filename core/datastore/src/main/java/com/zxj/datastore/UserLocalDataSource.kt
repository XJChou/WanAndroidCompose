package com.zxj.datastore

import androidx.datastore.core.DataStore
import com.zxj.model.RankBean
import com.zxj.model.UserBean
import com.zxj.model.UserScoreBean
import com.zxj.wanandroid.compose.datastore.UserPreferences
import com.zxj.wanandroid.compose.datastore.copy
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {

    val user: Flow<UserBean> = userPreferences.data.map {
        UserBean(
            id = it.id,
            icon = it.icon,
            username = it.username,
            email = it.email,
            type = it.type,
            chapterTops = null,
            collectIds = null
        )
    }

    private val _userInfo = MutableStateFlow<RankBean?>(null)
    val userInfo = _userInfo.asStateFlow()

    suspend fun updateUser(user: UserBean) {
        // 设置DataSource
        userPreferences.updateData {
            it.copy {
                id = user.id
                email = user.email
                icon = user.icon
                username = user.username
                type = user.type
            }
        }
    }

    fun saveUserInfo(userScoreBean: RankBean?) {
        _userInfo.value = userScoreBean
    }
}