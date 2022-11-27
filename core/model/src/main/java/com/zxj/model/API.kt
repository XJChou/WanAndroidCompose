package com.zxj.model

import java.io.IOException

class API<Data>(val errorCode: Int = 0, val errorMsg: String? = null, val data: Data? = null) {

    val errorMsgOrDefault: String
        get() = errorMsg ?: "网络异常"

    val isSuccess: Boolean
        get() = errorCode == 0

    fun ifSuccess(block: API<Data>.(data: Data?) -> Unit): API<Data> {
        if (isSuccess) {
            this.block(data)
        }
        return this
    }

    suspend fun ifSuspendSuccess(block: suspend API<Data>.(data: Data?) -> Unit): API<Data> {
        if (isSuccess) {
            this.block(data)
        }
        return this
    }


    fun ifError(block: API<Data>.(msg: String) -> Unit): API<Data> {
        if (!isSuccess) {
            this.block(errorMsg ?: "网络异常")
        }
        return this
    }

    suspend fun ifSuspendError(block: suspend API<Data>.(msg: String) -> Unit): API<Data> {
        if (!isSuccess) {
            this.block(errorMsg ?: "网络异常")
        }
        return this
    }
}


/**
 * 统一异常处理
 */
fun Throwable.toAPI(): API<*> {
    return when (this) {
        is IOException -> {
            API(-1, this.message, null)
        }
        else -> {
            API(-1, this.message, null)
        }
    }
}

