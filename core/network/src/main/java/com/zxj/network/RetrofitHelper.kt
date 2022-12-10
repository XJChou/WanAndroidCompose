package com.zxj.network

import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.lang.reflect.Type

fun Retrofit.inject(listener: ResponseInjectListener): Retrofit {
    val newCallAdapterFactories = this.callAdapterFactories().map { it.warp(listener) }
    return Retrofit.Builder()
        .baseUrl(baseUrl())
        .also { builder -> newCallAdapterFactories.forEach { builder.addCallAdapterFactory(it) } }
        .also { builder -> converterFactories().forEach { builder.addConverterFactory(it) } }
        .callFactory(callFactory())
        .also {
            val executor = callbackExecutor()
            if (executor != null) it.callbackExecutor(executor)
        }
        .build()
}

private fun CallAdapter.Factory.warp(listener: ResponseInjectListener): CallAdapter.Factory {
    val origin = this
    return object : CallAdapter.Factory() {
        override fun get(
            returnType: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): CallAdapter<*, *>? {
            return origin.get(returnType, annotations, retrofit)?.warp(listener)
        }
    }
}

private fun <T, R> CallAdapter<T, R>.warp(listener: ResponseInjectListener): CallAdapter<T, R> {
    val origin = this
    return object : CallAdapter<T, R> {
        override fun responseType(): Type = origin.responseType()

        override fun adapt(call: Call<T>): R = origin.adapt(call.warp(listener))
    }
}

private fun <T> Call<T>.warp(listener: ResponseInjectListener): Call<T> {
    val origin = this
    return object : Call<T> {
        override fun clone(): Call<T> = origin.clone()

        override fun execute(): Response<T> {
            val response = origin.execute()
            runCatching { listener.onResponse(this@warp, response) }
            return response
        }

        override fun enqueue(callback: Callback<T>) {
            return origin.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    callback.onResponse(call, response)
                    runCatching { listener.onResponse(this@warp, response) }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    callback.onFailure(call, t)
                }
            })
        }

        override fun isExecuted(): Boolean = origin.isExecuted

        override fun cancel() = origin.cancel()

        override fun isCanceled(): Boolean = origin.isCanceled

        override fun request(): Request = origin.request()

        override fun timeout(): Timeout = origin.timeout()
    }
}

interface ResponseInjectListener {
    fun <T> onResponse(call: Call<T>, response: Response<T>)
}