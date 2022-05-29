package com.zxj.wanandroid.compose.ui.page.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Dimension
import coil.size.OriginalSize
import coil.size.Size
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.getString
import com.zxj.wanandroid.compose.data.BannerBean
import com.zxj.wanandroid.compose.data.Data
import com.zxj.wanandroid.compose.ui.theme.Shapes
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.IndexViewModel

@Composable
fun IndexPage() {
    val indexViewModel: IndexViewModel = viewModel()
    indexViewModel.uiState
    val uiState by indexViewModel.uiState.collectAsState()
    // 这里应该套一个 refresh-layout
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(WanAndroidTheme.colors.windowBackground)
    ) {

        // banner view
        val bannerList = uiState.bannerList
        if (bannerList != null) {
            item { Banner(bannerList) }
        }

        // 列表内容
        val articleList = uiState.articleList
        if (articleList != null) {
            items(articleList.size) {
                ArticleItem(articleList[it])
            }
        }

        // viewpager
//        HorizontalPager()

        // 列表


    }
}

@Composable
fun ArticleItem(data: Data) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(WanAndroidTheme.colors.viewBackground)
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
            val author = if (data.author.isNullOrEmpty()) {
                data.shareUser
            } else {
                data.author
            }
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
            text = data.title,
            modifier = Modifier.padding(10.dp, 8.dp, 0.dp, 6.dp),
            fontSize = 16.sp,
            maxLines = 2,
            color = WanAndroidTheme.colors.itemTitle,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 25.sp
        )

        // 来源 + 点赞
        Row(Modifier.fillMaxWidth()) {
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
            Image(painter = painterResource(id = painter), contentDescription = null)
        }
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
fun Banner(bannerList: List<BannerBean>) {
    HorizontalPager(
        bannerList.size,
        Modifier.fillMaxWidth()
    ) { page ->
        val item = bannerList[page]
        Box(Modifier.fillMaxWidth()) {
            // 图片
            val imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(item.imagePath)
                .size(Size.ORIGINAL)
                .build()
            val painter = rememberAsyncImagePainter(imageRequest)
            Image(
                painter = painter,
                contentDescription = item.title,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
            // 文字
            Text(
                text = item.title,
                Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
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
            .padding(4.dp, 2.dp),
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