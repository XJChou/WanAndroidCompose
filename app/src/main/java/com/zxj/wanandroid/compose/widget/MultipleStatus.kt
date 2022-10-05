package com.zxj.wanandroid.compose.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 状态显示Composable
 */
@Composable
fun MultipleStatus(
    status: Status,
    modifier: Modifier = Modifier,
    initial: @Composable () -> Unit = {},
    loading: @Composable () -> Unit = {},
    error: @Composable (msg: String) -> Unit = {},
    retry: () -> Unit,
    content: @Composable Status.Success.() -> Unit,
) {
    Box(modifier) {
        when (status) {
            Status.Initial -> {
                Box(modifier = Modifier.matchParentSize()) {
                    initial()
                }
            }
            Status.Loading -> {
                Box(modifier = Modifier.matchParentSize()) {
                    loading()
                }
            }
            is Status.Success -> {
                Box(modifier = Modifier.matchParentSize()) {
                    status.content()
                }
            }
            is Status.Error -> {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable(onClick = retry)
                ) {
                    error(status.msg)
                }
            }
        }

    }
}

sealed interface Status {
    object Initial : Status
    object Loading : Status

    class Success(private val data: Any) : Status {

        @Suppress("UNCHECKED_CAST")
        fun <Data> getData(): Data = data as Data
    }

    class Error(val msg: String) : Status
}