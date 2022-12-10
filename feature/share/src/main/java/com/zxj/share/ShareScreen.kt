package com.zxj.share

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.map
import com.zxj.model.ArticleBean
import com.zxj.ui.PagingLazyColumn
import com.zxj.ui.TextToolBar
import com.zxj.ui.ToolBarIcon
import com.zxj.ui.ArticleItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import com.zxj.ui.R.drawable as UIDrawable

@Composable
fun ShareRoute(
    modifier: Modifier = Modifier,
    viewModel: ShareViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onBrowser: (String) -> Unit,
) {
    ShareScreen(
        onBack = onBack,
        pagingItems = viewModel.pagingItems.collectAsLazyPagingItems(),
        onArticleItem = { onBrowser(it.link) },
        onArticleCollectItem = viewModel::toggleCollect,
        modifier = modifier
    )

    LaunchedEffect(key1 = viewModel.collectFlow) {
        viewModel.collectFlow.collectLatest { event ->
            viewModel.pagingItems.map {
                it.map { item ->
                    if (item.id == event.first) {
                        item.copy(collect = item.collect)
                    } else {
                        item
                    }
                }
            }
        }
    }
}

@Composable
internal fun ShareScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onArticleItem: (ArticleBean) -> Unit,
    onArticleCollectItem: (Boolean, ArticleBean) -> Unit,
    pagingItems: LazyPagingItems<ArticleBean>,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TextToolBar(
                title = stringResource(id = R.string.my_share),
                navigationIcon = { ToolBarIcon(drawableRes = UIDrawable.ic_back, action = onBack) }
            )
        }
    ) { padding ->
        PagingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            pagingItems = pagingItems,
            padding = padding
        ) {
            items(pagingItems, key = { it.id }) {
                ArticleItem(
                    data = it!!,
                    onItemClick = onArticleItem,
                    onItemZanClick = onArticleCollectItem,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
