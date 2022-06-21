package com.zxj.wanandroid.compose.net

import com.zxj.wanandroid.compose.data.HttpConstant
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author chenxz
 * @date 2018/9/26
 * @desc SaveCookieInterceptor: 保存 Cookie
 */
class SaveCookieInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url.toString()
        val domain = request.url.host
        // set-cookie maybe has multi, login to save cookie
        if ((requestUrl.contains(HttpConstant.SAVE_USER_LOGIN_KEY)
                    || requestUrl.contains(HttpConstant.SAVE_USER_REGISTER_KEY))
            && response.headers(HttpConstant.SET_COOKIE_KEY).isNotEmpty()
        ) {
            val cookie = HttpConstant.encodeCookie(response.headers(HttpConstant.SET_COOKIE_KEY))
            HttpConstant.saveCookie(requestUrl, domain, cookie)
        }
        return response
    }
}