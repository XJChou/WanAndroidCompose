package com.zxj.wanandroid.compose.ui.collect

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.data.bean.CollectionArticle
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
    val pagingItems = viewModel.pageData.collectAsLazyPagingItems()
    CollectScreen(
        modifier = modifier,
        onBack = onBack,
        onItemClick = onItemClick,
        onRemoveCollect = viewModel::removeCollect,
        pagingItems = pagingItems
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
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
internal fun CollectScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onItemClick: (CollectionArticle) -> Unit = {},
    onRemoveCollect: (CollectionArticle) -> Unit = {},
    pagingItems: LazyPagingItems<CollectionArticle>
) {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val isRefresh by remember {
        derivedStateOf { pagingItems.loadState.refresh is LoadState.Loading }
    }
    val isLoading by remember {
        derivedStateOf { pagingItems.loadState.append is LoadState.Loading }
    }
    val isShowFloating by remember(state) {
        derivedStateOf { state.firstVisibleItemIndex > 5 }
    }
    Scaffold(
        topBar = {
            TextToolBar(
                title = stringResource(id = R.string.nav_my_collect),
                navigationIcon = {
                    ToolBarIcon(R.drawable.ic_back, onBack)
                }
            )
        },
        floatingActionButton = {
            AnimatedContent(isShowFloating) {
                if (it) {
                    FloatingActionButton(
                        onClick = {
                            scope.launch { state.animateScrollToItem(0, 0) }
                        },
                        backgroundColor = WanAndroidTheme.colors.colorPrimary
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_upward_white_24dp),
                            contentDescription = null
                        )
                    }
                }
            }
        },
    ) { padding ->
        SwipeRefresh(
            modifier = Modifier.padding(padding),
            state = rememberSwipeRefreshState(isRefreshing = isRefresh),
            onRefresh = { pagingItems.refresh() }) {
            LazyColumn(
                state = state
            ) {
                if (!isRefresh && !isLoading && pagingItems.itemCount == 0) {
                    item { EmptyData(modifier = modifier.fillParentMaxSize()) }
                } else {
                    items(pagingItems, { it.id }) {
                        CollectItem(
                            modifier = Modifier
                                .animateItemPlacement()
                                .padding(top = 1.dp),
                            collectionArticle = it!!,
                            onItemClick = onItemClick,
                            onRemoveCollect = onRemoveCollect
                        )
                    }
                    handleLoadState(pagingItems)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCollectScreen() {
    WanAndroidTheme {
//        CollectScreen(
//            refresh = false,
//            nextState = NextState.STATE_NONE,
//            dataList = arrayListOf(collectionArticleDemoData)
//        )
    }
}

