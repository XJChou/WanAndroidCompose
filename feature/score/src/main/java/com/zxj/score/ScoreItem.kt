package com.zxj.score

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.UserScoreBean
import com.zxj.model.userScoreBeanDemo

@Composable
fun ScoreItem(
    userScoreBean: UserScoreBean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(1f)
            ) {
                Text(
                    text = userScoreBean.reason,
                    color = WanAndroidTheme.colors.itemTitle,
                    fontSize = 16.sp
                )
                Text(
                    text = userScoreBean.desc,
                    modifier = Modifier.padding(top = 4.dp),
                    color = WanAndroidTheme.colors.itemDate
                )
            }
            Text(
                text = "+${userScoreBean.coinCount}",
                modifier = Modifier.align(Alignment.CenterVertically),
                color = WanAndroidTheme.colors.colorAccent
            )
        }
    }
}

@Preview
@Composable
fun PreviewScoreItem() {
    WanAndroidTheme {
        ScoreItem(userScoreBean = userScoreBeanDemo)
    }
}