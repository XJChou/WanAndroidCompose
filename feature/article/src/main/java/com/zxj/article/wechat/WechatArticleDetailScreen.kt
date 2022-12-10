package com.zxj.article.wechat

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.ArticleBean
import com.zxj.model.WechatChapterBean
import com.zxj.ui.PagingLazyColumn
import com.zxj.ui.ArticleItem
import com.zxj.ui.rememberLazyListState
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.flowOf

@Composable
fun WechatArticleDetailRoute(
    chapter: WechatChapterBean,
    modifier: Modifier = Modifier,
    wechatArticleDetailViewModel: WechatArticleDetailViewModel = wechatArticleDetailViewModel(
        chapter.id
    ),
    onBrowser: (String) -> Unit
) {
    val pagingItems = wechatArticleDetailViewModel.pagingData.collectAsLazyPagingItems()
    WechatArticleDetailScreen(
        pagingItems = pagingItems,
        modifier = modifier,
        onCollectClick = { collect, article ->
            wechatArticleDetailViewModel.collectArticle(collect, article.id)
        },
        onArticleClick = {
            onBrowser(it.link)
        },
        getCollect = wechatArticleDetailViewModel::getCollect
    )
}

@Composable
private fun WechatArticleDetailScreen(
    pagingItems: LazyPagingItems<ArticleBean>,
    modifier: Modifier = Modifier,
    onArticleClick: (ArticleBean) -> Unit,
    onCollectClick: (Boolean, ArticleBean) -> Unit,
    getCollect: (String) -> Boolean?
) {
    PagingLazyColumn(
        modifier = modifier,
        pagingItems = pagingItems,
        state = pagingItems.rememberLazyListState()
    ) {
        items(pagingItems) {
            ArticleItem(
                data = it!!,
                collect = getCollect(it.id) ?: it.collect,
                modifier = Modifier.fillMaxWidth(),
                onItemZanClick = onCollectClick,
                onItemClick = onArticleClick
            )
        }
    }
}

/**
 * 解决方法
 *
 * 1. https://stackoverflow.com/questions/70174711/how-to-do-assisted-injection-with-navigation-compose
 * 2. https://medium.com/scalereal/providing-assistedinject-supported-viewmodel-for-composable-using-hilt-ae973632e29a
 */
@Composable
fun wechatArticleDetailViewModel(cid: Int): WechatArticleDetailViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).wechatArticleDetailViewModel()
    return viewModel(
        factory = WechatArticleDetailViewModel.provideFactory(factory, cid),
        key = "chapter - ${cid}"
    )
}

@Preview
@Composable
fun PreviewWechatArticleDetailScreen() {
    WanAndroidTheme {
        WechatArticleDetailScreen(
            pagingItems = flowOf(PagingData.empty<ArticleBean>()).collectAsLazyPagingItems(),
            onArticleClick = {},
            onCollectClick = { _, _ -> },
            getCollect = { false }
        )
    }
}