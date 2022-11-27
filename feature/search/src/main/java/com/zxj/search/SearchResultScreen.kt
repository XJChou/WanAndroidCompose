package com.zxj.search

import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.fillMaxSize
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
        modifier = modifier
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
    // 事件处理
    LaunchedEffect(viewModel.collectFlow) {
        viewModel.collectFlow.collect { event ->
            viewModel.pagingItems.map {
                it.map {
                    if (it.id == event.first) {
                        it.copy(collect = event.second)
                    } else {
                        it
                    }
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
                    onItemZanClick = onItemZan,
                    onItemClick = onItemClick,
                    modifier = modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
