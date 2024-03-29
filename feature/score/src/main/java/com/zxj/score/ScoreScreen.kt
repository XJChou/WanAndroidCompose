package com.zxj.score

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.UserScoreBean
import com.zxj.ui.PagingLazyColumn
import com.zxj.ui.TextToolBar
import com.zxj.ui.ToolBarIcon
import kotlinx.coroutines.flow.flowOf
import com.zxj.ui.R.drawable as UIDrawable


@Composable
fun ScoreRoute(
    modifier: Modifier = Modifier,
    scoreViewModel: ScoreViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onBrowser: (String) -> Unit
) {
    val pagingItems = scoreViewModel.pagingItems.collectAsLazyPagingItems()
    val score by scoreViewModel.scoreFlow.collectAsState(initial = 0)

    ScoreScreen(
        modifier = modifier,
        onBack = onBack,
        onQuestion = { onBrowser("https://www.wanandroid.com/blog/show/2653") },
        pagingItems = pagingItems,
        score = score
    )
}

@Composable
fun ScoreScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onQuestion: () -> Unit,
    pagingItems: LazyPagingItems<UserScoreBean>,
    score: Int? = null
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TextToolBar(
                title = stringResource(id = R.string.nav_my_score),
                navigationIcon = {
                    ToolBarIcon(UIDrawable.ic_back, onBack)
                },
                actions = {
                    ToolBarIcon(R.drawable.ic_help_white_24dp, onQuestion)
                }
            )
        }
    ) {
        PagingLazyColumn(modifier = Modifier, pagingItems = pagingItems, padding = it) {
            if (pagingItems.itemCount != 0) {
                item(contentType = 1) {
                    Box(
                        modifier = Modifier
                            .background(WanAndroidTheme.colors.colorPrimary)
                            .fillMaxWidth()
                            .height(150.dp)
                    ) {
                        Text(
                            text = "${(score ?: "--")}",
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 50.sp,
                            color = Color.White
                        )

                    }
                }
            }
            items(pagingItems, key = { it.id }) {
                ScoreItem(userScoreBean = it!!)
            }
        }
    }
}

@Preview
@Composable
fun PreviewScoreScreen() {
    ScoreScreen(
        onBack = {},
        onQuestion = {},
        pagingItems = flowOf(PagingData.empty<UserScoreBean>()).collectAsLazyPagingItems(),
        score = null
    )
}