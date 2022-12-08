package com.zxj.article.square

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zxj.model.ArticleBean
import com.zxj.ui.ArticleItem
import com.zxj.ui.PagingLazyColumn
import com.zxj.ui.rememberLazyListState

@Composable
fun SquareRoute(
    modifier: Modifier = Modifier,
    viewModel: SquareViewModel = hiltViewModel(),
    navigateToBrowser: (String) -> Unit,
) {
    val pagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()
    SquareScreen(
        pagingItems = pagingItems,
        modifier = modifier,
        navigateToBrowser = navigateToBrowser,
        getCollect = viewModel::getCollect,
        onArticleCollect = viewModel::articleCollect
    )
}

@Composable
fun SquareScreen(
    pagingItems: LazyPagingItems<ArticleBean>,
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit,
    getCollect: (String) -> Boolean?,
    onArticleCollect: (Boolean, ArticleBean) -> Unit
) {
    PagingLazyColumn(
        modifier = modifier,
        pagingItems = pagingItems,
        state = pagingItems.rememberLazyListState()
    ) {
        items(pagingItems) {
            ArticleItem(
                data = it!!,
                modifier = Modifier
                    .fillMaxWidth(),
                collect = getCollect(it.id) ?: it.collect,
                onItemClick = { navigateToBrowser(it.link) },
                onItemZanClick = onArticleCollect
            )
        }
    }
}
