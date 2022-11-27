package com.zxj.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

fun LazyListScope.handleLoadState(items: LazyPagingItems<*>) {
    val append = items.loadState.append
    when {
        append is LoadState.NotLoading -> {
            // 当前条目数量不为空 且 已经全部加载完成
            if (items.itemCount != 0 && append.endOfPaginationReached) {
                item {
                    ShowLoadFinish(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(48.dp)
                    )
                }
            }
        }

        append is LoadState.Loading -> {
            item { ShowLoading() }
        }

        append is LoadState.Error -> {
            item {
                ShowLoadError(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(48.dp)
                        .clickable(onClick = items::retry)
                )
            }
        }
    }
}

@Composable
private fun ShowLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(48.dp)
    ) {
        LoadingWheel(
            contentDesc = "加载中",
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun ShowLoadFinish(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = "已全部加载",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ShowLoadError(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = "加载失败",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}