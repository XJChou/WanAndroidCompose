package com.zxj.ui

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
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun <Item : Any> PagingLazyColumn(
    modifier: Modifier,
    pagingItems: LazyPagingItems<Item>,
    padding: PaddingValues = PaddingValues(0.dp),
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

@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState {
    // After recreation, LazyPagingItems first return 0 items, then the cached items.
    // This behavior/issue is resetting the LazyListState scroll position.
    // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
    return when (itemCount) {
        // Return a different LazyListState instance.
        0 -> remember(this) { LazyListState(0, 0) }
        // Return rememberLazyListState (normal case).
        else -> androidx.compose.foundation.lazy.rememberLazyListState()
    }
}