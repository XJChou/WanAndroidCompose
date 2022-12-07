package com.zxj.ui

import android.text.Html
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.ArticleBean
import com.zxj.model.articleBeanDemo


@Composable
fun ArticleItem(
    data: ArticleBean,
    modifier: Modifier = Modifier,
    collect: Boolean = data.collect,
    onItemZanClick: (collect: Boolean, data: ArticleBean) -> Unit = { _, _ -> },
    onItemClick: (data: ArticleBean) -> Unit = {},
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(WanAndroidTheme.colors.viewBackground)
            .clickable {
                onItemClick.invoke(data)
            }
            .padding(0.dp, 10.dp, 10.dp, 10.dp)
    ) {
        // 标签 + 作者 + 时间
        Row(Modifier) {
            // 标签 [置顶 + 刷新 + Tag]
            if (data.top == "1") {
                ArticleTag(text = stringResource(R.string.top_tip), Color.Red)
            }
            if (data.fresh) {
                ArticleTag(text = stringResource(R.string.new_fresh), Color.Red)
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
            val painter = remember(collect) {
                if (collect) R.drawable.ic_like else R.drawable.ic_like_not
            }
            Image(
                painter = painterResource(id = painter),
                contentDescription = null,
                modifier = Modifier.clickable(
                    onClick = {
                        onItemZanClick.invoke(
                            !collect,
                            data
                        )
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
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

@Preview
@Composable
fun PreviewArticleItem() {
    ArticleItem(articleBeanDemo)
}

@Preview
@Composable
fun PrevArticleTag() {
    ArticleTag(text = "测试", color = Color.Black)
}