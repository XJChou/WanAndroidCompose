package com.zxj.collect

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.CollectionBean
import com.zxj.ui.PagingLazyColumn
import com.zxj.ui.TextToolBar
import com.zxj.ui.ToolBarIcon
import kotlinx.coroutines.launch
import com.zxj.ui.R.drawable as UIDrawable

/**
 * 隔离ViewModel内容
 */
@Composable
fun CollectRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onItemClick: (CollectionBean) -> Unit,
    viewModel: CollectViewModel = hiltViewModel()
) {
    val pagingItems = viewModel.pagingData.collectAsLazyPagingItems()
    CollectScreen(
        modifier = modifier,
        onBack = onBack,
        onItemClick = onItemClick,
        onRemoveCollect = viewModel::removeCollect,
        pagingItems = pagingItems
    )
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect {
            when (it) {
                is CollectUIEvent.ShowToast -> {
                    Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
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
    onBack: () -> Unit,
    onItemClick: (CollectionBean) -> Unit,
    onRemoveCollect: (CollectionBean) -> Unit,
    pagingItems: LazyPagingItems<CollectionBean>
) {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val isShowFloating by remember(state) { derivedStateOf { state.firstVisibleItemIndex > 5 } }
    Scaffold(
        modifier = modifier,
        topBar = {
            TextToolBar(
                title = stringResource(id = R.string.nav_my_collect),
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
            pagingItems = pagingItems,
            padding = padding,
            state = state,
            modifier = Modifier.fillMaxSize()
        ) {
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
        }
    }
}
