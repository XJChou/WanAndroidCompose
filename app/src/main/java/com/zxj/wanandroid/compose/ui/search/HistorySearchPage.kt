package com.zxj.wanandroid.compose.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.GetString
import com.zxj.wanandroid.compose.data.bean.HistorySearchBean
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import java.util.*

@Composable
fun ColumnScope.HistorySearchPage(
    searchHistoryList: List<HistorySearchBean>,
    onHistoryItemClick: (HistorySearchBean) -> Unit,
    onHistoryItemDelete: (HistorySearchBean) -> Unit,
    onClearHistoryClick: () -> Unit
) {
    // 标头
    SearchPageHead(GetString(id = R.string.history_search), onClearHistoryClick)

    // 列表
    LazyColumn(
        content = {
            if (searchHistoryList.isEmpty()) {
                item(contentType = 0) { HistorySearchEmptyPage() }
            } else {
                items(searchHistoryList.size, contentType = { 1 }) {
                    HistorySearchItem(
                        searchHistoryList[it],
                        onHistoryItemClick,
                        onHistoryItemDelete
                    )
                }
            }
        },
        modifier = Modifier.padding(top = 12.dp)
    )
}

@Composable
private fun SearchPageHead(text: String, onClear: () -> Unit) {
    Row(
        modifier = Modifier.padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = WanAndroidTheme.colors.colorAccent,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = GetString(id = R.string.clear_all),
            color = Color(0xFFBDBDBD),
            fontSize = 14.sp,
            modifier = Modifier
                .clickable(
                    onClick = onClear,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(6.dp)
        )
    }
}

@Composable
private fun HistorySearchEmptyPage() {
    Text(
        text = GetString(id = R.string.search_null_tint),
        color = Color(0xFFBDBDBD),
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth(1f)
    )
}

@Composable
private fun HistorySearchItem(
    historySearchBean: HistorySearchBean,
    onItemClick: (HistorySearchBean) -> Unit,
    onItemDelete: (HistorySearchBean) -> Unit
) {
    Row(
        modifier = Modifier
            .background(WanAndroidTheme.colors.viewBackground)
            .clickable { onItemClick(historySearchBean) }
            .padding(10.dp)
            .fillMaxWidth(1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = historySearchBean.search,
            color = WanAndroidTheme.colors.itemDesc,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(R.drawable.ic_clear_grey_24dp),
            tint = Color(0xFFBDBDBD),
            contentDescription = null,
            modifier = Modifier
                .size(18.dp)
                .clickable(
                    onClick = { onItemDelete(historySearchBean) },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
        )
    }
}


@Preview
@Composable
fun PreviewHistorySearchItem() {
    HistorySearchItem(
        historySearchBean = HistorySearchBean("测试", Date()),
        onItemClick = {},
        onItemDelete = {}
    )
}

@Preview
@Composable
fun PreviewHistorySearchEmpty() {
    HistorySearchEmptyPage()
}