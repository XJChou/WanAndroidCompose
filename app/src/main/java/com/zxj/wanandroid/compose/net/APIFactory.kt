package com.zxj.wanandroid.compose.net

import com.ayvytr.okhttploginterceptor.LoggingInterceptor
import com.zxj.wanandroid.compose.data.HttpConstant
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.fastjson.FastJsonConverterFactory
import retrofit2.create

object APIFactory {

    val retrofit by lazy { buildRetrofit() }

    private val okHttpClient by lazy { buildOKHttpClient() }

    inline fun <reified T> get(): T {
        return retrofit.create()
    }

    private fun buildRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com")
        .addCallAdapterFactory(APICallAdapterFactory())
        .addConverterFactory(FastJsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private fun buildOKHttpClient(): OkHttpClient = OkHttpClient.Builder()
//        .addInterceptor(HeaderInterceptor())
//        .addInterceptor(SaveCookieInterceptor())
        .addInterceptor(LoggingInterceptor(true, visualFormat = false, isShowAll = true))
        .cookieJar(WanAndroidCookieJar)
        .build()

    private object WanAndroidCookieJar : CookieJar {
        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            val domain = url.host
            val urlString = url.toString()
            if (domain.isNotEmpty() && (urlString.contains(HttpConstant.COLLECTIONS_WEBSITE)
                        || urlString.contains(HttpConstant.UNCOLLECTIONS_WEBSITE)
                        || urlString.contains(HttpConstant.ARTICLE_WEBSITE)
                        || urlString.contains(HttpConstant.TODO_WEBSITE)
                        || urlString.contains(HttpConstant.COIN_WEBSITE))
            ) {
                return HttpConstant.getCookies(url)
            }
            return arrayListOf()
        }

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            val urlString = url.toString()
            if ((urlString.contains(HttpConstant.SAVE_USER_LOGIN_KEY)
                        || urlString.contains(HttpConstant.SAVE_USER_REGISTER_KEY))
            ) {
                HttpConstant.saveCookie(cookies)
            }
        }

    }
}
