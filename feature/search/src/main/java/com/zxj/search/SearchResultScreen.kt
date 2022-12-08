package com.zxj.search

import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.flow.map
import com.zxj.ui.R.drawable as UIDrawable

@Composable
fun SearchResultRoute(
    onBack: () -> Unit,
    onItemClick: (ArticleBean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel()
) {
    val pagingItems = viewModel.pagingItems.collectAsLazyPagingItems()
    SearchResultScreen(
        content = viewModel.searchContext,
        onBack = onBack,
        onItemClick = onItemClick,
        onItemZan = viewModel::toggleCollect,
        pagingItems = pagingItems,
        modifier = modifier,
        getCollect = viewModel::getCollect
    )
    // toast处理
    val context = LocalContext.current
    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect {
            when (it) {
                is UIEvent.ShowToast -> {
                    Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@VisibleForTesting
@Composable
internal fun SearchResultScreen(
    content: String,
    onBack: () -> Unit,
    onItemClick: (ArticleBean) -> Unit,
    onItemZan: (Boolean, ArticleBean) -> Unit,
    pagingItems: LazyPagingItems<ArticleBean>,
    modifier: Modifier = Modifier,
    getCollect: (String) -> Boolean?
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TextToolBar(
                content,
                navigationIcon = {
                    ToolBarIcon(drawableRes = UIDrawable.ic_back, onBack)
                }
            )
        }
    ) { padding ->
        PagingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            pagingItems = pagingItems,
            padding = padding,
        ) {
            items(pagingItems, key = { it.id }) {
                ArticleItem(
                    data = it!!,
                    collect = getCollect(it.id) ?: it.collect,
                    onItemZanClick = onItemZan,
                    onItemClick = onItemClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
