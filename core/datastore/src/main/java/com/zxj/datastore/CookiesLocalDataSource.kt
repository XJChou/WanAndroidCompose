package com.zxj.datastore

import androidx.datastore.core.DataStore
import com.zxj.wanandroid.compose.datastore.CookiesPreferences
import com.zxj.wanandroid.compose.datastore.copy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class CookiesLocalDataSource @Inject constructor(
    private val cookiesPreferences: DataStore<CookiesPreferences>
) {

    val isLogin = cookiesPreferences.data.map { it.isLogin }

    fun saveCookies(cookies: List<String>) {
        runBlocking {
            cookiesPreferences.updateData {
                it.copy {
                    this.isLogin = true
                    this.cookies.clear()
                    this.cookies += cookies
                }
            }
        }
    }

    fun getCookies(): List<String> {
        return runBlocking {
            cookiesPreferences.data.first().cookiesList
        }
    }

    suspend fun clearToken() {
        cookiesPreferences.updateData {
            it.copy {
                clearIsLogin()
                cookies.clear()
            }
        }
    }

    fun clearTokenSync() {
        runBlocking { clearToken() }
    }
}