package com.zxj.rank

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
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.RankBean
import com.zxj.ui.TextToolBar
import com.zxj.ui.ToolBarIcon
import com.zxj.ui.PagingLazyColumn
import kotlinx.coroutines.launch
import com.zxj.ui.R.drawable as UIDrawable

@Composable
fun RankRoute(
    modifier: Modifier = Modifier,
    viewModel: RankViewModel = hiltViewModel(),
    onBack: () -> Unit,
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
    pagingItems: LazyPagingItems<RankBean>,
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
                    ToolBarIcon(UIDrawable.ic_back, onBack)
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
                            painter = painterResource(id = UIDrawable.ic_arrow_upward_white_24dp),
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
                RankItem(rankBean = it!!)
            }
        }
    }
}