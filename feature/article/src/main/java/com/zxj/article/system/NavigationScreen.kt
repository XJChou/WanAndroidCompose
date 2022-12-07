package com.zxj.article.system

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.items
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowRow
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.NavigationBean
import com.zxj.ui.MultipleStatus
import com.zxj.ui.randomColor
import kotlinx.coroutines.launch

private const val CONTENT_TYPE_TITLE = "CONTENT_TYPE_TITLE"
private const val CONTENT_TYPE_CONTENT = "CONTENT_TYPE_CONTENT"

@Composable
internal fun NavigationRoute(
    modifier: Modifier = Modifier,
    viewModel: NavigationViewModel = hiltViewModel(),
    navigateToBrowser: (String) -> Unit
) {
    val status by viewModel.uiState.collectAsState()
    MultipleStatus(
        modifier = modifier,
        status = status,
        retry = viewModel::loadData
    ) {
        NavigationScreen(
            navigations = getData(),
            modifier = Modifier.fillMaxSize(),
            navigateToBrowser = navigateToBrowser
        )
    }
}

@Composable
private fun NavigationScreen(
    navigations: List<NavigationBean>?,
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit
) {
    Row(modifier = modifier.background(Color.Cyan)) {
        val tagListState = rememberLazyListState()
        val articleListState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        // 导航名字
        LazyColumn(
            modifier = Modifier
                .width(110.dp)
                .fillMaxHeight()
                .background(WanAndroidTheme.colors.verticalTabLayoutBg),
        ) {
            if (navigations != null) {
                itemsIndexed(navigations) { index, it ->
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    val nowPageIndex = articleListState.firstVisibleItemIndex
                                    if (nowPageIndex < index * 2 || nowPageIndex > (index * 2 + 1)) {
                                        articleListState.animateScrollToItem(index * 2)
                                    }
                                }
                            }
                            .fillMaxWidth()
                            .height(50.dp)
                            .wrapContentSize(),
                    )
                }
            }
        }


        // 所有列表
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(WanAndroidTheme.colors.viewBackground),
            state = articleListState
        ) {
            navigations?.forEach {
                item(key = it.name, contentType = CONTENT_TYPE_TITLE) {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                        fontSize = 16.sp,
                        color = WanAndroidTheme.colors.itemTitle
                    )
                }
                item(key = it.cid, contentType = CONTENT_TYPE_CONTENT) {
                    FlowRow(
                        modifier = Modifier.padding(
                            start = 22.dp,
                            end = 22.dp,
                            bottom = 22.dp,
                            top = 12.dp
                        )
                    ) {
                        it.articles.forEach {
                            Text(
                                text = it.title,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .clickable { navigateToBrowser(it.link) }
                                    .background(WanAndroidTheme.colors.itemFlowLayoutBg)
                                    .padding(horizontal = 10.dp, vertical = 10.dp),
                                color = Color(rememberSaveable(it.link) { randomColor() }),
                            )
                        }
                    }
                }
            }
        }

    }
}