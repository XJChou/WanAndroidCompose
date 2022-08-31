package com.zxj.wanandroid.compose.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun SmartLazyColumn(
    modifier: Modifier = Modifier,
    refreshState: SwipeRefreshState = rememberSwipeRefreshState(isRefreshing = false),
    onRefresh: () -> Unit,
    nextState: NextState = rememberNextState(),
    onPageNext: () -> Unit,
    state: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit
) {
    SwipeRefresh(state = refreshState, onRefresh = onRefresh, modifier = modifier) {
        // 自动刷新处理
        LazyColumn(
            state = state,
            content = {
                content()

                when (val stateValue = nextState.state) {

                    // 加载中
                    NextState.STATE_LOADING -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .height(48.dp)
                            ) {
                                LoadingWheel(
                                    "加载中",
                                    Modifier
                                        .size(36.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }

                    // 加载成功
                    NextState.STATE_FINISH_OVER, NextState.STATE_FINISH_PART -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .height(48.dp)
                            ) {
                                Text(
                                    text = if (stateValue == NextState.STATE_FINISH_OVER) "已全部加载" else "加载成功",
                                    modifier = Modifier.align(Alignment.Center)
                                )
                                // 当加载成功的时候重组，触发加载
                                SideEffect {
                                    if (stateValue == NextState.STATE_FINISH_PART) onPageNext()
                                }
                            }
                        }
                    }

                    // 加载失败
                    NextState.STATE_ERROR -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .height(48.dp)
                                    .clickable { onPageNext() }
                            ) {
                                Text(
                                    text = "加载失败",
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun rememberNextState(state: Int = NextState.STATE_NONE): NextState {
    return remember { NextState(state) }
}

class NextState(state: Int = STATE_NONE) {
    companion object {
        const val STATE_NONE = 0
        const val STATE_LOADING = 1
        const val STATE_FINISH_OVER = 2
        const val STATE_FINISH_PART = 3
        const val STATE_ERROR = 4
    }

    var state: Int by mutableStateOf(state)
}