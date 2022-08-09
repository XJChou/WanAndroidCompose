package com.zxj.wanandroid.compose.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.zxj.wanandroid.compose.application.ComposeApplication
import com.zxj.wanandroid.compose.data.datasource.CookiesPreferencesSerializer
import com.zxj.wanandroid.compose.datastore.CookiesPreferences
import com.zxj.wanandroid.compose.datastore.copy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Cookie
import okhttp3.HttpUrl

private val Context.cookiesPreferences: DataStore<CookiesPreferences> by dataStore(
    fileName = "cookies_preferences.pb",
    serializer = CookiesPreferencesSerializer(),
)

private val cookiesPreferences = ComposeApplication.application.cookiesPreferences

/**
 * Created by chenxz on 2018/6/9.
 */
object HttpConstant {

    const val SAVE_USER_LOGIN_KEY = "user/login"
    const val SAVE_USER_REGISTER_KEY = "user/register"

    const val COLLECTIONS_WEBSITE = "lg/collect"
    const val UNCOLLECTIONS_WEBSITE = "lg/uncollect"
    const val ARTICLE_WEBSITE = "article"
    const val TODO_WEBSITE = "lg/todo"
    const val COIN_WEBSITE = "lg/coin"

    const val ALREADY_LOGIN_OUT = -1001

    val isLogin: Flow<Boolean> = cookiesPreferences.data.map {
        it.isLogin
    }

    fun saveCookie(newCookies: List<Cookie>) {
        runBlocking {
            cookiesPreferences.updateData {
                it.copy {
                    isLogin = true
                    cookies.clear()
                    cookies.addAll(newCookies.map { it.toString() })
                }
            }
        }
    }

    fun getCookies(url: HttpUrl): List<Cookie> {
        return runBlocking {
            cookiesPreferences.data.first().cookiesList.map {
                Cookie.parse(url, it)!!
            }
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