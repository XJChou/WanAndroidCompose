package com.zxj.wanandroid.compose.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.getString
import com.zxj.wanandroid.compose.data.bean.CollectionArticle
import com.zxj.wanandroid.compose.data.bean.collectionArticleDemoData
import com.zxj.wanandroid.compose.ui.theme.Shapes
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.utils.clickableNotEffect

@Composable
fun CollectItem(
    modifier: Modifier = Modifier,
    collectionArticle: CollectionArticle,
    onItemClick: (CollectionArticle) -> Unit,
    onRemoveCollect: (CollectionArticle) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(1f)
            .clip(Shapes.medium)
            .clickable { onItemClick.invoke(collectionArticle) }
            .background(WanAndroidTheme.colors.viewBackground)
            .padding(0.dp, 10.dp, 10.dp, 10.dp)
    ) {

        Row(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                text = collectionArticle.author.ifEmpty { getString(R.string.anonymous) },
                color = WanAndroidTheme.colors.itemAuthor,
                modifier = Modifier.weight(1f),
                fontSize = 12.sp
            )
            Text(
                text = collectionArticle.niceDate,
                color = WanAndroidTheme.colors.itemAuthor,
                fontSize = 12.sp
            )
        }

        Box {

            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(1f)
            ) {

                // thumbnail
                val envelopePic = collectionArticle.envelopePic
                if (envelopePic.isNotEmpty()) {
                    AsyncImage(
                        model = envelopePic,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(120.dp, 90.dp)
                    )
                }


                Column(
                    modifier = Modifier
                        .padding(10.dp, 10.dp, 0.dp, 0.dp)
                        .fillMaxWidth(1f)
                ) {
                    // title
                    Text(
                        text = collectionArticle.title,
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .fillMaxWidth(1f),
                        maxLines = 2,
                        fontSize = 16.sp,
                        color = WanAndroidTheme.colors.itemTitle
                    )

                    // 描述
                    Text(
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 10.dp, 0.dp)
                            .fillMaxHeight(1f)
                            .wrapContentHeight(Alignment.CenterVertically),
                        text = collectionArticle.chapterName,
                        color = WanAndroidTheme.colors.itemChapter,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clickableNotEffect { onRemoveCollect.invoke(collectionArticle) }
            )
        }
    }

}

@Preview
@Composable
fun PreviewCollectItem() {
    WanAndroidTheme {
        CollectItem(
            modifier = Modifier,
            collectionArticleDemoData,
            onItemClick = {},
            onRemoveCollect = {}
        )
    }
}