package com.zxj.wanandroid.compose.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.zxj.wanandroid.compose.data.bean.User
import com.zxj.wanandroid.compose.data.bean.UserInfoBody
import com.zxj.wanandroid.compose.datastore.UserPreferences
import com.zxj.wanandroid.compose.datastore.copy
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject

val Context.userPreferences: DataStore<UserPreferences> by dataStore(
    fileName = "user_preferences.pb",
    serializer = UserPreferencesSerializer(),
)

class UserLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val user: Flow<User> = context.userPreferences.data.map {
        User(it.id, it.icon, it.username, it.email, it.type)
    }

    private val _userInfo = MutableStateFlow<UserInfoBody?>(null)
    val userInfo = _userInfo.asStateFlow()

    suspend fun updateUser(user: User) {
        // 设置DataSource
        context.userPreferences.updateData {
            it.copy {
                id = user.id
                email = user.email
                icon = user.icon
                username = user.username
                type = user.type
            }
        }
    }

    fun saveUserInfo(userInfoBody: UserInfoBody?) {
        _userInfo.value = userInfoBody
    }
}