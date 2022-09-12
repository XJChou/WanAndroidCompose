package com.zxj.wanandroid.compose.ui.rank

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zxj.wanandroid.compose.data.bean.CoinInfoBean
import com.zxj.wanandroid.compose.data.bean.coinInfoBeanDemoData
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

@Composable
fun RankItem(
    coinInfoBean: CoinInfoBean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${coinInfoBean.rank}",
            modifier = Modifier.width(60.dp),
            color = WanAndroidTheme.colors.itemAuthor,
            textAlign = TextAlign.Center
        )
        Text(
            text = coinInfoBean.username,
            fontSize = 16.sp,
            color = WanAndroidTheme.colors.itemTitle,
            modifier = Modifier
                .padding(end = 4.dp)
                .weight(1f)
        )
        Text(
            text = "${coinInfoBean.coinCount}",
            color = WanAndroidTheme.colors.colorAccent,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
fun PreviewRankItem() {
    WanAndroidTheme {
        RankItem(coinInfoBean = coinInfoBeanDemoData)
    }
}