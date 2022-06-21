package com.zxj.wanandroid.compose.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zxj.wanandroid.compose.application.ComposeApplication
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val Context.cookiesPreferences by preferencesDataStore("cookies")

private val cookiesPreferences = ComposeApplication.application.cookiesPreferences

private val loginKey = booleanPreferencesKey("isLogin")

/**
 * Created by chenxz on 2018/6/9.
 */
object HttpConstant {

    const val DEFAULT_TIMEOUT: Long = 15
    const val SAVE_USER_LOGIN_KEY = "user/login"
    const val SAVE_USER_REGISTER_KEY = "user/register"

    const val COLLECTIONS_WEBSITE = "lg/collect"
    const val UNCOLLECTIONS_WEBSITE = "lg/uncollect"
    const val ARTICLE_WEBSITE = "article"
    const val TODO_WEBSITE = "lg/todo"
    const val COIN_WEBSITE = "lg/coin"

    const val SET_COOKIE_KEY = "set-cookie"
    const val COOKIE_NAME = "Cookie"

    const val MAX_CACHE_SIZE: Long = 1024 * 1024 * 50 // 50M 的缓存大小

    val isLogin: Flow<Boolean> = cookiesPreferences.data.map {
        it[loginKey] ?: false
    }

    fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
            .map { cookie ->
                cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }
            .forEach {
                it.filterNot { set.contains(it) }.forEach { set.add(it) }
            }
        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

    fun saveCookie(url: String, domain: String, cookies: String) {
        runBlocking {
            val key = stringPreferencesKey(url)
            cookiesPreferences.edit {
                val domainKey = stringPreferencesKey(domain)
                // key = url, value = cookies
                it[key] = cookies
                // key = domain, value = cookies
                it[domainKey] = cookies
                it[loginKey] = true
            }
        }
    }

    fun getCookies(url: String): String {
        return runBlocking {
            val domainKey = stringPreferencesKey(url)
            cookiesPreferences.data.first()[domainKey] ?: ""
        }
    }

    suspend fun clearToken() {
        cookiesPreferences.edit {
            it.clear()
        }
    }
}