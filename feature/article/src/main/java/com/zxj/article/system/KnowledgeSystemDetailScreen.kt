package com.zxj.article.system

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentColor
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.zxj.model.Knowledge
import com.zxj.ui.*
import com.zxj.ui.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
internal fun KnowledgeSystemDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: KnowledgeSystemDetailViewModel = hiltViewModel(),
    onBack: () -> Unit,
    navigateToBrowser: (String) -> Unit
) {
    KnowledgeSystemDetailScreen(
        title = viewModel.name,
        knowledges = viewModel.knowledges,
        modifier = modifier,
        onBack = onBack,
        getPagingItems = viewModel::getPagingItems,
        getCollect = viewModel::getCollect,
        navigateToBrowser = navigateToBrowser,
        onCollectClick = viewModel::collectArticle
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
private fun KnowledgeSystemDetailScreen(
    title: String,
    knowledges: List<Knowledge>,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    getPagingItems: (Int) -> Flow<PagingData<ArticleBean>>,
    getCollect: (String) -> Boolean?,
    onCollectClick: (Boolean, ArticleBean) -> Unit,
    navigateToBrowser: (String) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TextToolBar(
                modifier = Modifier.fillMaxWidth(),
                title = title,
                fitsSystemWindows = true,
                navigationIcon = {
                    ToolBarIcon(drawableRes = R.drawable.ic_back, onBack)
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            val pagerState = rememberPagerState()
            val currentPage by remember { derivedStateOf { pagerState.currentPage } }
            val scope = rememberCoroutineScope()

            ScrollableTabRow(
                selectedTabIndex = currentPage,
                edgePadding = 0.dp,
                backgroundColor = WanAndroidTheme.colors.colorPrimary,
                contentColor = Color.White,
            ) {
                knowledges.forEachIndexed { index, knowledge ->
                    Tab(
                        selected = index == currentPage,
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(20.dp, 15.dp),
                            text = knowledge.name,
                            color = if (currentPage == index) LocalContentColor.current else LocalContentColor.current.copy(
                                0.7f
                            )
                        )
                    }
                }
            }

            HorizontalPager(
                count = knowledges.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pagerState
            ) {
                KnowledgeSystemDetailPage(
                    pagingItems = getPagingItems(knowledges[it].id).collectAsLazyPagingItems(),
                    modifier = Modifier.fillMaxSize(),
                    getCollect = getCollect,
                    navigateToBrowser = navigateToBrowser,
                    onCollectClick = onCollectClick,
                )
            }
        }
    }
}

@Composable
private fun KnowledgeSystemDetailPage(
    pagingItems: LazyPagingItems<ArticleBean>,
    modifier: Modifier = Modifier,
    getCollect: (String) -> Boolean?,
    navigateToBrowser: (String) -> Unit,
    onCollectClick: (Boolean, ArticleBean) -> Unit,
) {
    PagingLazyColumn(
        modifier = modifier,
        pagingItems = pagingItems,
        state = pagingItems.rememberLazyListState(),
    ) {
        items(pagingItems) {
            ArticleItem(
                data = it!!,
                collect = getCollect(it.id) ?: it.collect,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                onItemClick = { navigateToBrowser(it.link) },
                onItemZanClick = onCollectClick
            )
        }
    }
}