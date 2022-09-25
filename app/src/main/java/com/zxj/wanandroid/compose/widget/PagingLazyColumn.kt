package com.zxj.wanandroid.compose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

@Composable
fun <Item : Any> PagingLazyColumn(
    modifier: Modifier,
    pagingItems: LazyPagingItems<Item>,
    padding: PaddingValues,
    state: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit,
) {
    val isRefresh by remember {
        derivedStateOf { pagingItems.loadState.refresh is LoadState.Loading }
    }
    val isShowEmpty by remember { derivedStateOf { pagingItems.itemCount == 0 } }
    SwipeRefresh(
        modifier = Modifier.padding(padding),
        state = rememberSwipeRefreshState(isRefreshing = isRefresh),
        onRefresh = { pagingItems.refresh() }) {
        LazyColumn(state = state, modifier = modifier) {
            if (isShowEmpty) {
                item { EmptyData(modifier = modifier.fillParentMaxSize()) }
            } else {
                content()
                handleLoadState(pagingItems)
            }
        }
    }
}