package com.zxj.article.index

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zxj.model.ArticleBean
import com.zxj.ui.*

@Composable
fun IndexRoute(
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit,
    indexViewModel: IndexViewModel = hiltViewModel()
) {
    val uiState by indexViewModel.uiState.collectAsState()
    val pagingItems = indexViewModel.pageItems.collectAsLazyPagingItems()
    IndexScreen(
        bannerList = uiState.bannerList,
        modifier = modifier,
        onBrowser = navigateToBrowser,
        onItemCollect = indexViewModel::dealZanAction,
        pagingItems = pagingItems,
    )
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        indexViewModel.uiEvent.collect {
            when (it) {
                is UIEvent.ShowToast -> {
                    Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
private fun IndexScreen(
    bannerList: List<com.zxj.model.BannerBean>,
    pagingItems: LazyPagingItems<ArticleBean>,
    modifier: Modifier = Modifier,
    onBrowser: (String) -> Unit,
    onItemCollect: (Boolean, ArticleBean) -> Unit
) {
    val state = pagingItems.rememberLazyListState()
    PagingLazyColumn(
        state = state,
        modifier = modifier,
        pagingItems = pagingItems
    ) {
        // BannerList 点击
        if (bannerList.isNotEmpty()) {
            item(contentType = 0) {
                Banner(Modifier.fillMaxWidth(), bannerList = bannerList) {
                    onBrowser(it.url)
                }
            }
        }
        items(pagingItems, key = { it.id }) {
            ArticleItem(
                it!!,
                onItemZanClick = onItemCollect,
                onItemClick = { onBrowser(it.link) }
            )
        }
        handleLoadState(pagingItems)
    }
}
