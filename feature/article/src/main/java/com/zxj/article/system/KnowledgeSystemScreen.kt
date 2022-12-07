package com.zxj.article.system

import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.Knowledge
import com.zxj.model.KnowledgeTreeBean
import com.zxj.ui.MultipleStatus

@Composable
internal fun KnowledgeSystemRoute(
    modifier: Modifier = Modifier,
    viewModel: KnowledgeSystemViewModel = hiltViewModel(),
    navigateToKnowledgeSystemDetail: (String, List<Knowledge>) -> Unit
) {
    val status by viewModel.uiState.collectAsState()
    MultipleStatus(modifier = modifier, status = status, retry = viewModel::loadData) {
        KnowledgeSystemScreen(
            data = getData(),
            modifier = Modifier.fillMaxSize(),
            navigateToKnowledgeSystemDetail = navigateToKnowledgeSystemDetail
        )
    }
}

@Composable
private fun KnowledgeSystemScreen(
    data: List<KnowledgeTreeBean>?,
    modifier: Modifier = Modifier,
    navigateToKnowledgeSystemDetail: (String, List<Knowledge>) -> Unit
) {
    LazyColumn(modifier = modifier) {
        if (data != null) {
            items(data) {
                KnowledgeSystemItem(
                    knowledgeTreeBean = it,
                    modifier = Modifier.fillMaxWidth(),
                    onItemClick = {
                        navigateToKnowledgeSystemDetail(it.name, it.children)
                    }
                )
            }
        }
    }
}

@Composable
private fun KnowledgeSystemItem(
    knowledgeTreeBean: KnowledgeTreeBean,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {
    val listDivideColor = WanAndroidTheme.colors.listDivider
    Row(
        modifier = modifier
            .clickable(onClick = onItemClick)
            .background(WanAndroidTheme.colors.viewBackground)
            .drawBehind {
                drawLine(
                    color = listDivideColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                )
            }
            .padding(10.dp)
            .height(IntrinsicSize.Min)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = knowledgeTreeBean.name,
                color = WanAndroidTheme.colors.itemTitle,
                fontSize = 16.sp
            )
            Text(
                text = remember(knowledgeTreeBean.children) {
                    knowledgeTreeBean.children.joinToString("    ", transform = { child ->
                        Html.fromHtml(child.name)
                    })
                },
                modifier = Modifier.padding(top = 10.dp),
                color = WanAndroidTheme.colors.itemDesc,
                lineHeight = 18.sp
            )
        }
        Image(
            painter = painterResource(id = com.zxj.ui.R.drawable.ic_arrow_right_24dp),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(24.dp)
        )
    }
}