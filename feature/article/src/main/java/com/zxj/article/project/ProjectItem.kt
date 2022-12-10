package com.zxj.article.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.ArticleBean
import com.zxj.model.articleBeanDemo

private val ITEM_PROJECT_HEIGHT = 180.dp

@Composable
fun ProjectItem(
    data: ArticleBean,
    modifier: Modifier = Modifier,
    onCollectItem: (ArticleBean, Boolean) -> Unit,
    getCollect: (String) -> Boolean?,
    onItemClick: (ArticleBean) -> Unit
) {
    val divideColor = WanAndroidTheme.colors.listDivider
    ConstraintLayout(
        modifier = modifier
            .clickable { onItemClick(data) }
            .background(WanAndroidTheme.colors.viewBackground)
            .drawBehind {
                drawLine(
                    divideColor,
                    Offset(0f, size.height),
                    Offset(size.width, size.height),
                )
            }
            .padding(10.dp)
            .fillMaxWidth()
            .height(ITEM_PROJECT_HEIGHT)
    ) {
        val (
            imageRef, titleRef, descRef,
            authorRef, dateRef,
            collectRef, spaceRef
        ) = createRefs()
        // 图片
        Image(
            painter = rememberAsyncImagePainter(model = data.envelopePic),
            contentDescription = null,
            modifier = Modifier.constrainAs(imageRef) {
                start.linkTo(parent.start)
                linkTo(parent.top, parent.bottom)
                height = Dimension.fillToConstraints
            },
            contentScale = ContentScale.FillHeight
        )

        // 文字
        Text(
            text = data.title,
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(parent.top, 8.dp)
                linkTo(imageRef.end, parent.end, 20.dp)
                width = Dimension.fillToConstraints
            },
            fontSize = 16.sp,
            color = WanAndroidTheme.colors.itemTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // 描述
        Text(
            text = data.desc,
            modifier = Modifier.constrainAs(descRef) {
                top.linkTo(titleRef.bottom, 10.dp)
                linkTo(titleRef.start, titleRef.end)
                width = Dimension.fillToConstraints
            },
            fontSize = 14.sp,
            color = WanAndroidTheme.colors.itemDesc,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        // 作者
        Text(
            text = data.author,
            modifier = Modifier.constrainAs(authorRef) {
                bottom.linkTo(collectRef.top, 10.dp)
                start.linkTo(titleRef.start)
                width = Dimension.fillToConstraints
            },
            fontSize = 12.sp,
            color = WanAndroidTheme.colors.itemAuthor
        )

        // 时间
        Text(
            text = data.niceDate,
            modifier = Modifier.constrainAs(dateRef) {
                top.linkTo(authorRef.top)
                end.linkTo(titleRef.end)
                width = Dimension.fillToConstraints
            },
            fontSize = 12.sp,
            color = WanAndroidTheme.colors.itemDate
        )

        // 收藏
        val collect by remember(data) { derivedStateOf { getCollect(data.id) ?: data.collect } }
        Image(
            painter = painterResource(id = if (collect) com.zxj.ui.R.drawable.ic_like else com.zxj.ui.R.drawable.ic_like_not),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(collectRef) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .clickable { onCollectItem(data, !collect) }
        )
    }
}

@Preview
@Composable
private fun PreviewProjectItem() {
    WanAndroidTheme {
        ProjectItem(
            data = articleBeanDemo,
            onCollectItem = { _, _ -> },
            onItemClick = {},
            modifier = Modifier.fillMaxWidth(),
            getCollect = { false }
        )
    }
}