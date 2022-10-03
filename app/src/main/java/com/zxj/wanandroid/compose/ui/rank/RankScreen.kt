package com.zxj.wanandroid.compose.ui.rank

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.navigation.animation.composable
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.Screen
import com.zxj.wanandroid.compose.data.bean.CoinInfoBean
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.widget.PagingLazyColumn
import com.zxj.wanandroid.compose.widget.TextToolBar
import com.zxj.wanandroid.compose.widget.ToolBarIcon
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addRankScreen(
    controller: NavHostController
) {
    composable(route = Screen.Rank.route) {
        RankRoute(
            onBack = { controller.popBackStack() },
            modifier = Modifier
        )
    }
}

@Composable
fun RankRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RankViewModel = hiltViewModel()
) {
    val pagingItems = viewModel.pagingData.collectAsLazyPagingItems()
    RankScreen(
        onBack = onBack,
        modifier = modifier.fillMaxSize(1f),
        pagingItems = pagingItems
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun RankScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<CoinInfoBean>,
) {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val isShowFloating by remember(state) { derivedStateOf { state.firstVisibleItemIndex > 5 } }
    Scaffold(
        modifier = modifier,
        topBar = {
            TextToolBar(
                title = stringResource(id = R.string.score_list),
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
        PagingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            pagingItems = pagingItems,
            padding = padding,
            state = state
        ) {
            items(pagingItems, key = { it.userId }) {
                RankItem(coinInfoBean = it!!)
            }
        }
    }
}