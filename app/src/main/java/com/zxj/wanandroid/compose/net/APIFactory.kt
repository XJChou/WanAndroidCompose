package com.zxj.wanandroid.compose.net

import com.ayvytr.okhttploginterceptor.LoggingInterceptor
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
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(SaveCookieInterceptor())
        .addInterceptor(LoggingInterceptor(true, visualFormat = false, isShowAll = true))
        .build()
}
