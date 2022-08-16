package com.zxj.wanandroid.compose.ui.screen.home

import android.text.Html
import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.getString
import com.zxj.wanandroid.compose.data.bean.Data
import com.zxj.wanandroid.compose.ui.screen.view.Banner
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.FetchStatus
import com.zxj.wanandroid.compose.viewmodel.IndexViewAction
import com.zxj.wanandroid.compose.viewmodel.IndexViewModel

@Composable
fun IndexPage(
    onItemClick: (Data) -> Unit
) {
    val indexViewModel: IndexViewModel = viewModel(LocalContext.current as ComponentActivity)

    // StateFlow -> State
    val uiState by indexViewModel.uiState.collectAsState()

    // 是否当前在刷新状态
    val state = remember { SwipeRefreshState(false) }
    state.isRefreshing = uiState.refreshFetchStatus == FetchStatus.Fetching
    // 轮播列表
    val bannerList by remember { derivedStateOf { uiState.bannerList } }
    // 文章列表
    val articleList by remember { derivedStateOf { uiState.articleList } }
    // 是否能够加载
    val hasLoad by remember { derivedStateOf { uiState.hasLoad } }

    // 刷新布局
    SwipeRefresh(
        state = state,
        onRefresh = {
            indexViewModel.dispatch(IndexViewAction.RefreshAction)
        }
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .background(WanAndroidTheme.colors.windowBackground),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {

            // 轮播内容
            bannerList?.also {
                item {
                    Banner(
                        Modifier.fillMaxWidth(),
                        bannerList = it
                    ) {
                    }
                }
            }

            // 列表内容
            articleList?.also { dataList ->
                items(dataList.size) {
                    ArticleItem(dataList[it],
                    onItemClickListener = onItemClick)
                }
            }

            // 加载内容
            if (hasLoad) {
                item {
                    val text = remember(uiState.isLoad) {
                        if (uiState.isLoad) {
                            "加载中"
                        } else {
                            "点击加载"
                        }
                    }
                    Text(
                        text = text,
                        modifier = Modifier.clickable {
                            indexViewModel.dispatch(IndexViewAction.LoadAction)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleItem(
    data: Data,
    onItemZanListener: ((targetZan: Int, data: Data) -> Unit)? = null,
    onItemClickListener: ((data: Data) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(WanAndroidTheme.colors.viewBackground)
            .clickable {
                onItemClickListener?.invoke(data)
            }
            .padding(0.dp, 10.dp, 10.dp, 10.dp)
    ) {
        // 标签 + 作者 + 时间
        Row(Modifier) {
            // 标签 [置顶 + 刷新 + Tag]
            if (data.top == "1") {
                ArticleTag(text = getString(R.string.top_tip), Color.Red)
            }
            if (data.fresh) {
                ArticleTag(text = getString(R.string.new_fresh), Color.Red)
            }
            if (data.tags.isNotEmpty()) {
                ArticleTag(text = data.tags.first().name, WanAndroidTheme.colors.colorAccent)
            }

            // 作者
            val author = data.author.ifEmpty { data.shareUser }
            Text(
                text = author,
                Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .weight(1f),
                color = WanAndroidTheme.colors.itemAuthor,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis
            )

            // 时间
            Text(text = data.niceDate, color = WanAndroidTheme.colors.itemDate, fontSize = 12.sp)
        }


        // 标题
        Text(
            text = Html.fromHtml(data.title).toString(),
            modifier = Modifier.padding(10.dp, 8.dp, 0.dp, 6.dp),
            fontSize = 16.sp,
            maxLines = 2,
            color = WanAndroidTheme.colors.itemTitle,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 25.sp
        )

        // 来源 + 点赞
        Row(Modifier.fillMaxWidth())  {
            val chapterName = remember(data.superChapterName, data.chapterName) {
                when {
                    data.superChapterName.isNotEmpty() and data.chapterName.isNotEmpty() -> {
                        "${data.superChapterName} / ${data.chapterName}"
                    }
                    data.superChapterName.isNotEmpty() -> {
                        data.superChapterName
                    }
                    data.chapterName.isNotEmpty() -> {
                        data.chapterName
                    }
                    else -> ""
                }
            }
            Text(
                text = chapterName,
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                color = WanAndroidTheme.colors.itemChapter,
                fontSize = 12.sp
            )
            val painter = remember(data.zan) {
                if (data.zan == 1) R.drawable.ic_like else R.drawable.ic_like_not
            }
            Image(
                painter = painterResource(id = painter),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onItemZanListener?.invoke(
                        if (data.zan == 1) 0 else 1,
                        data
                    )
                }
            )
        }
    }
}


@Composable
fun ArticleTag(text: String, color: Color) {
    Text(
        text = text,
        modifier = Modifier
            .padding(10.dp, 0.dp, 0.dp, 0.dp)
            .border(BorderStroke(0.5.dp, color), RoundedCornerShape(2.dp))
            .padding(4.5.dp, 2.dp),
        color = color,
        fontSize = 10.sp
    )
}

//@Preview
//@Composable
//fun PreviewIndex() {
//    WanAndroidTheme {
//        IndexPage()
//    }
//}


@Preview
@Composable
fun PreviewData() {
    WanAndroidTheme {
        ArticleItem(data = Data.Demo)
    }
}