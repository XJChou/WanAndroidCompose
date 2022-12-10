package com.zxj.article.project

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalContentColor
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.ArticleBean
import com.zxj.model.ProjectTreeBean
import com.zxj.ui.MultipleStatus
import com.zxj.ui.PagingLazyColumn
import com.zxj.ui.rememberLazyListState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
internal fun ProjectRoute(
    modifier: Modifier = Modifier,
    viewModel: ProjectViewModel = hiltViewModel(),
    navigateToBrowser: (String) -> Unit
) {
    val status by viewModel.uiState.collectAsState()
    MultipleStatus(status = status, retry = viewModel::loadData) {
        ProjectScreen(
            projectTree = getData(),
            modifier = modifier,
            navigateToBrowser = navigateToBrowser,
            getPagingItems = viewModel::getPagingFlow,
            getCollect = viewModel::getCollect,
            onCollectItem = viewModel::collectItem
        )
    }

    val context = LocalContext.current
    LaunchedEffect(viewModel) {
        // toast
        viewModel.event.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ProjectScreen(
    projectTree: List<ProjectTreeBean>?,
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit,
    getPagingItems: (Int) -> Flow<PagingData<ArticleBean>>,
    getCollect: (String) -> Boolean?,
    onCollectItem: (ArticleBean, Boolean) -> Unit
) {
    val pagerState = rememberPagerState()
    val selectedTabIndex by remember(pagerState) {
        derivedStateOf { pagerState.currentPage }
    }
    val scope = rememberCoroutineScope()
    Column(modifier = modifier) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 0.dp,
            backgroundColor = WanAndroidTheme.colors.colorPrimary,
            contentColor = Color.White,
        ) {
            projectTree?.forEachIndexed { index, project ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { scope.launch { pagerState.scrollToPage(index) } }) {
                    Text(
                        modifier = Modifier.padding(20.dp, 15.dp),
                        text = project.name,
                        color = if (selectedTabIndex == index) LocalContentColor.current else LocalContentColor.current.copy(
                            0.7f
                        )
                    )
                }
            }
        }
        HorizontalPager(
            count = projectTree?.size ?: 0,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState,
            key = { projectTree?.get(it)?.id ?: 0 }
        ) {
            val project = projectTree?.get(it)!!
            ProjectPage(
                pagingItems = getPagingItems(project.id).collectAsLazyPagingItems(),
                modifier = Modifier.fillMaxSize(),
                navigateToBrowser = navigateToBrowser,
                onCollectItem = onCollectItem,
                getCollect = getCollect
            )
        }
    }
}

@Composable
private fun ProjectPage(
    pagingItems: LazyPagingItems<ArticleBean>,
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit,
    getCollect: (String) -> Boolean?,
    onCollectItem: (ArticleBean, Boolean) -> Unit
) {
    // Tips：LazyPagingItem 重组的时候，会先数据为0，然后再恢复数据
    PagingLazyColumn(
        modifier = modifier,
        pagingItems = pagingItems,
        state = pagingItems.rememberLazyListState()
    ) {
        items(pagingItems) {
            ProjectItem(
                data = it!!,
                modifier = Modifier.fillMaxWidth(),
                getCollect = getCollect,
                onItemClick = { navigateToBrowser(it.link) },
                onCollectItem = onCollectItem
            )
        }
    }
}