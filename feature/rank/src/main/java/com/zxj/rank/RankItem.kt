package com.zxj.rank

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.RankBean
import com.zxj.model.rankBeanDemo

@Composable
fun RankItem(
    rankBean: RankBean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${rankBean.rank}",
            modifier = Modifier.width(60.dp),
            color = WanAndroidTheme.colors.itemAuthor,
            textAlign = TextAlign.Center
        )
        Text(
            text = rankBean.username,
            fontSize = 16.sp,
            color = WanAndroidTheme.colors.itemTitle,
            modifier = Modifier
                .padding(end = 4.dp)
                .weight(1f)
        )
        Text(
            text = "${rankBean.coinCount}",
            color = WanAndroidTheme.colors.colorAccent,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
fun PreviewRankItem() {
    RankItem(
        rankBean = rankBeanDemo,
        modifier = Modifier.fillMaxWidth()
    )
}