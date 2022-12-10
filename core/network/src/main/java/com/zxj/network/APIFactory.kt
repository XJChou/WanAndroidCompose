package com.zxj.network

import com.ayvytr.okhttploginterceptor.LoggingInterceptor
import com.zxj.datastore.CookiesLocalDataSource
import com.zxj.model.API
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.fastjson.FastJsonConverterFactory
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton

const val ERROR_CODE_NEED_LOGIN = -1001

private val saveUserMatches = arrayOf("user/login", "user/register")

private val readUserMatches = arrayOf(
    "lg/collect", "lg/uncollect", "article", "lg/todo", "lg/coin"
)

@Singleton
class APIFactory @Inject constructor(
    private val cookiesLocalDataSource: CookiesLocalDataSource
) {

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
        .inject(object : ResponseInjectListener {
            override fun <T> onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body is API<*>) {
                    when (body.errorCode) {
                        ERROR_CODE_NEED_LOGIN -> {
                            cookiesLocalDataSource.clearTokenSync()
                        }
                    }
                }
            }
        })

    private fun buildOKHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor(true, visualFormat = false, isShowAll = true))
        .cookieJar(object : CookieJar {
            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val domain = url.host
                val urlString = url.toString()
                if (domain.isNotEmpty() && readUserMatches.any { urlString.contains(it) }) {
                    return cookiesLocalDataSource.getCookies().map {
                        Cookie.parse(url, it)!!
                    }
                }
                return arrayListOf()
            }

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                val urlString = url.toString()
                if (saveUserMatches.any { urlString.contains(it) }) {
                    cookiesLocalDataSource.saveCookies(cookies.map { it.toString() })
                }
            }
        })
        .build()
}