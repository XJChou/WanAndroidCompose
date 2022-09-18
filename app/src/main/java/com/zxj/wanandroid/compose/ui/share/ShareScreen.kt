package com.zxj.wanandroid.compose.ui.share

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.data.bean.*
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.widget.*

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addShareScreen(controller: NavHostController) {
    composable(route = NavigationRoute.SHARE) {
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

}

@Composable
internal fun ShareScreen(
    modifier: Modifier = Modifier,
    shareArticles: List<Article>? = emptyList(),
    onBack: () -> Unit = defaultBack,
    onArticleItem: (Article) -> Unit = defaultArticleItemClick,
    onArticleCollectItem: (Boolean, Article) -> Unit = defaultArticleCollectClick,
    onRefresh: () -> Unit = defaultRefresh,
    isRefreshing: Boolean = false,
    onPageNext: () -> Unit = defaultLoad,
    nextState: Int = NextState.STATE_NONE
) {
    Column(modifier = modifier.fillMaxSize(1f)) {
        TextToolBar(
            title = stringResource(id = R.string.my_share),
            navigationIcon = { ToolBarIcon(drawableRes = R.drawable.ic_back, action = onBack) }
        )
        SmartLazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(1f),
            onRefresh = onRefresh,
            refreshState = rememberSwipeRefreshState(isRefreshing),
            onPageNext = onPageNext,
            nextState = rememberNextState(nextState)
        ) {
            if (shareArticles == null) {
                return@SmartLazyColumn
            }
            if (shareArticles.isEmpty()) {
                item(contentType = 1) { EmptyData(modifier = Modifier.fillParentMaxSize()) }
            } else {
                items(shareArticles) {
                    ArticleItem(
                        data = it,
                        onItemClick = onArticleItem,
                        onItemZanClick = onArticleCollectItem
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewShareScreen() {
    WanAndroidTheme {
        ShareScreen()
    }
}