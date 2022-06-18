package com.zxj.wanandroid.compose.data.datasource

import androidx.datastore.core.DataStore
import com.zxj.wanandroid.compose.data.bean.User
import com.zxj.wanandroid.compose.datastore.UserPreferences
import com.zxj.wanandroid.compose.datastore.copy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {

    val user: Flow<User> = userPreferences.data.map {
        User(it.id, it.icon, it.username, it.password, it.email, it.token, it.type)
    }

    suspend fun updateUser(user: User) {
        // 设置DataSource
        userPreferences.updateData {
            it.copy {
                id = user.id
                email = user.email
                icon = user.icon
                username = user.username
                password = user.password
                token = user.token
                type = user.type
            }
        }

    }

    suspend fun clearToken() {
        userPreferences.updateData {
            it.copy { token = "" }
        }
    }
}