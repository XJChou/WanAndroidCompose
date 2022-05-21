package com.zxj.wanandroid.compose.utils

import android.text.TextUtils
import android.util.Log
import java.lang.Exception

object MyLog {
    private var isLogEnable = false

    fun init(isLogOpen: Boolean) {
        isLogEnable = isLogOpen
    }

    private val fileLineMethod: String
        private get() {
            val traceElement = Exception().stackTrace[2]
            val toStringBuffer = StringBuffer("[").append(traceElement.fileName).append(" | ")
                .append(traceElement.lineNumber).append(" | ").append(traceElement.methodName)
                .append("]")
            return toStringBuffer.toString()
        }

    fun i(msg: String) {
        show(Log.INFO, fileLineMethod, msg)
    }

    fun d(msg: String) {
        show(Log.DEBUG, fileLineMethod, msg)
    }

    fun e(msg: String) {
        show(Log.ERROR, fileLineMethod, msg)
    }

    fun v(msg: String) {
        show(Log.VERBOSE, fileLineMethod, msg)
    }

    fun w(msg: String) {
        show(Log.WARN, fileLineMethod, msg)
    }

    fun i(tag: String, msg: String) {
        show(Log.INFO, tag, msg)
    }

    fun d(tag: String, msg: String) {
        show(Log.DEBUG, tag, msg)
    }

    fun e(tag: String, msg: String) {
        show(Log.ERROR, tag, msg)
    }

    fun v(tag: String, msg: String) {
        show(Log.VERBOSE, tag, msg)
    }

    fun w(tag: String, msg: String) {
        show(Log.WARN, tag, msg)
    }

    private fun show(level: Int, tag: String, message: String) {
        val myTag="MyLog:${tag}"
        if (isLogEnable && !TextUtils.isEmpty(message) && !TextUtils.isEmpty(myTag)) {
            if (message.length > 3000) {
                var endLength = 0
                while (endLength < message.length) {
                    showLog(
                        level,
                        myTag,
                        message.substring(
                            endLength,
                            Math.min(message.length, 3000.let { endLength += it; endLength })
                        )
                    )
                }
            } else {
                showLog(level, myTag, message)
            }
        }
    }

    private fun showLog(level: Int, tag: String, message: String) {
        when (level) {
            Log.VERBOSE -> Log.v(tag, message)
            Log.DEBUG -> Log.d(tag, message)
            Log.INFO -> Log.i(tag, message)
            Log.WARN -> Log.w(tag, message)
            Log.ERROR -> Log.e(tag, message)
            Log.ASSERT -> i(tag, message)
        }
    }
}