package com.zxj.wanandroid.compose.ui.home.wechat

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zxj.wanandroid.compose.Screen
import com.zxj.wanandroid.compose.data.bean.Article
import com.zxj.wanandroid.compose.data.bean.WXChapterBean
import com.zxj.wanandroid.compose.data.di.ViewModelFactoryProvider
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.widget.ArticleItem
import com.zxj.wanandroid.compose.widget.PagingLazyColumn
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.flowOf

@Composable
fun WechatArticleDetailRoute(
    chapter: WXChapterBean,
    modifier: Modifier = Modifier,
    wechatArticleDetailViewModel: WechatArticleDetailViewModel = wechatArticleDetailViewModel(
        chapter.id
    )
) {
    val pagingItems = wechatArticleDetailViewModel.pagingData.collectAsLazyPagingItems()
    WechatArticleDetailScreen(
        pagingItems = pagingItems,
        modifier = modifier,
        onCollectClick = { collect, article ->
            wechatArticleDetailViewModel.collectArticle(collect, article.id)
        },
        onArticleClick = {
            Screen.Web.browser(it.link)
        }
    )
}

@Composable
private fun WechatArticleDetailScreen(
    pagingItems: LazyPagingItems<Article>,
    modifier: Modifier = Modifier,
    onArticleClick: (Article) -> Unit,
    onCollectClick: (Boolean, Article) -> Unit
) {
    PagingLazyColumn(modifier = modifier, pagingItems = pagingItems) {
        items(pagingItems) {
            ArticleItem(
                data = it!!,
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
            pagingItems = flowOf(PagingData.empty<Article>()).collectAsLazyPagingItems(),
            onArticleClick = {},
            onCollectClick = { _, _ -> }
        )
    }
}