package com.zxj.wanandroid.compose.ui.collect

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.data.bean.CollectionArticle
import com.zxj.wanandroid.compose.data.bean.collectionArticleDemoData
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.widget.*
import kotlinx.coroutines.launch

/**
 * 添加搜索结果界面到Navigation
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addCollectScreen(
    controller: NavHostController
) {
    composable(route = NavigationRoute.COLLECT) {
        CollectRoute(
            modifier = Modifier.fillMaxSize(1f),
            onBack = { controller.popBackStack() },
            onItemClick = { controller.navigate(NavigationRoute.buildBrowserRoute(it.link)) },
        )
    }
}


/**
 * 隔离ViewModel内容
 */
@Composable
fun CollectRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onItemClick: (CollectionArticle) -> Unit = {},
    viewModel: CollectViewModel = hiltViewModel()
) {
    val collectUiState by viewModel.uiState.collectAsState()
    CollectScreen(
        modifier = modifier,
        onBack = onBack,
        refresh = collectUiState.refresh,
        onRefresh = viewModel::refresh,
        nextState = collectUiState.nextState,
        onNextPage = viewModel::nextPage,
        onItemClick = onItemClick,
        onRemoveCollect = viewModel::removeCollect,
        dataList = collectUiState.dataList
    )
    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect {
            when (it) {
                is CollectUIEvent.ShowToast -> {
                    toast(it.msg)
                }
            }
        }
    }
}

/**
 * 只负责响应和向上传递事件
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CollectScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    refresh: Boolean,
    onRefresh: () -> Unit = {},
    nextState: Int,
    onNextPage: () -> Unit = {},
    onItemClick: (CollectionArticle) -> Unit = {},
    onRemoveCollect: (CollectionArticle) -> Unit = {},
    dataList: List<CollectionArticle>?
) {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.combinedClickable(
            onDoubleClick = {
                scope.launch {
                    state.scrollToItem(0, 0)
                }
            },
            onClick = {},
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )
    ) {
        TextToolBar(
            title = stringResource(id = R.string.nav_my_collect),
            navigationIcon = {
                ToolBarIcon(R.drawable.ic_back, onBack)
            }
        )

        SmartLazyColumn(
            modifier = Modifier.fillMaxSize(),
            refreshState = rememberSwipeRefreshState(isRefreshing = false).also {
                it.isRefreshing = refresh
            },
            onRefresh = onRefresh,
            nextState = rememberNextState().also {
                it.state = nextState
            },
            onPageNext = onNextPage,
            state = state
        ) {
            if (dataList != null) {
                if (dataList.isNotEmpty()) {
                    items(dataList, key = { it.id }) {
                        CollectItem(
                            modifier = Modifier.animateItemPlacement().padding(top = 1.dp),
                            collectionArticle = it,
                            onItemClick = onItemClick,
                            onRemoveCollect = onRemoveCollect
                        )
                    }
                } else {
                    item(contentType = { 1 }) {
                        EmptyData(modifier = modifier.fillParentMaxSize())
                    }
                }
            }
        }

    }

}

@Preview
@Composable
fun PreviewCollectScreen() {
    WanAndroidTheme {
        CollectScreen(
            refresh = false,
            nextState = NextState.STATE_NONE,
            dataList = arrayListOf(collectionArticleDemoData)
        )
    }
}

