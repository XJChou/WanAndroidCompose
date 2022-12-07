package com.zxj.article.system

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentColor
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.zxj.article.R
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.Knowledge
import kotlinx.coroutines.launch

private val PAGES = listOf(R.string.knowledge_system, R.string.navigation)

@Composable
internal fun SystemRoute(
    modifier: Modifier = Modifier,
    viewModel: SystemViewModel = hiltViewModel(),
    navigateToBrowser: (String) -> Unit,
    navigateToKnowledgeSystemDetail: (String, List<Knowledge>) -> Unit
) {
    SystemScreen(
        modifier = modifier,
        navigateToBrowser = navigateToBrowser,
        navigateToKnowledgeSystemDetail = navigateToKnowledgeSystemDetail
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun SystemScreen(
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit,
    navigateToKnowledgeSystemDetail: (String, List<Knowledge>) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val currentPage by remember { derivedStateOf { pagerState.currentPage } }
    Column(modifier = modifier) {
        ScrollableTabRow(
            selectedTabIndex = currentPage,
            edgePadding = 0.dp,
            backgroundColor = WanAndroidTheme.colors.colorPrimary,
            contentColor = Color.White,
        ) {
            PAGES.forEachIndexed { index, system ->
                Tab(
                    selected = currentPage == index,
                    onClick = { scope.launch { pagerState.scrollToPage(index) } }) {
                    androidx.compose.material3.Text(
                        modifier = Modifier.padding(20.dp, 15.dp),
                        text = stringResource(id = system),
                        color = if (currentPage == index) LocalContentColor.current else LocalContentColor.current.copy(
                            0.7f
                        )
                    )
                }
            }
        }
        HorizontalPager(
            count = PAGES.size,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState,
            key = { PAGES[it] },
            content = {
                when (PAGES[it]) {
                    R.string.knowledge_system -> {
                        KnowledgeSystemRoute(
                            modifier = Modifier.fillMaxSize(),
                            navigateToKnowledgeSystemDetail = navigateToKnowledgeSystemDetail
                        )
                    }
                    R.string.navigation -> {
                        NavigationRoute(
                            modifier = Modifier.fillMaxSize(),
                            navigateToBrowser = navigateToBrowser
                        )
                    }
                }
            }
        )
    }
}