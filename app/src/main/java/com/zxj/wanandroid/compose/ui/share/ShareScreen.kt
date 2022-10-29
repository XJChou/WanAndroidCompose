package com.zxj.wanandroid.compose.ui.share

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.navigation.animation.composable
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.Screen
import com.zxj.wanandroid.compose.data.bean.Article
import com.zxj.wanandroid.compose.data.bean.defaultArticleCollectClick
import com.zxj.wanandroid.compose.data.bean.defaultArticleItemClick
import com.zxj.wanandroid.compose.data.bean.defaultBack
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.widget.ArticleItem
import com.zxj.wanandroid.compose.widget.PagingLazyColumn
import com.zxj.wanandroid.compose.widget.TextToolBar
import com.zxj.wanandroid.compose.widget.ToolBarIcon

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addShareScreen(controller: NavHostController) {
    composable(route = Screen.Share.route) {
        ShareRoute(
            onBack = { controller.popBackStack() }
        )
    }
}

@Composable
fun ShareRoute(
    modifier: Modifier = Modifier,
    viewModel: ShareViewModel = hiltViewModel(),
    onBack: () -> Unit = defaultBack,
) {
    ShareScreen(
        onBack = onBack,
        pagingItems = viewModel.pagingItems.collectAsLazyPagingItems(),
        onArticleItem = {

        },
        onArticleCollectItem = { b, article ->

        },
        modifier = modifier
    )
}

@Composable
internal fun ShareScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onArticleItem: (Article) -> Unit = defaultArticleItemClick,
    onArticleCollectItem: (Boolean, Article) -> Unit = defaultArticleCollectClick,
    pagingItems: LazyPagingItems<Article>,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TextToolBar(
                title = stringResource(id = R.string.my_share),
                navigationIcon = { ToolBarIcon(drawableRes = R.drawable.ic_back, action = onBack) }
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
                    onItemZanClick = onArticleCollectItem
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewShareScreen() {
    WanAndroidTheme {
    }
}