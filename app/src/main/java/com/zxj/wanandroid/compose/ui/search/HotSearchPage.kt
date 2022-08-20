package com.zxj.wanandroid.compose.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.GetString
import com.zxj.wanandroid.compose.data.bean.HotSearchBean
import com.zxj.wanandroid.compose.ui.theme.Shapes
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

@Composable
fun ColumnScope.HotSearchPage(
    hotSearchList: List<HotSearchBean>,
    hotSearchColorList: List<Color>,
    onHotItemClick: (HotSearchBean) -> Unit
) {
    Text(
        text = GetString(id = R.string.hot_search),
        color = WanAndroidTheme.colors.colorAccent,
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    FlowRow(modifier = Modifier.fillMaxWidth(1f)) {
        hotSearchList.forEachIndexed { index, item ->
            Text(
                text = item.name,
                modifier = Modifier
                    .padding(5.dp)
                    .background(Color(0xFFEEEEEE), Shapes.medium)
                    .clickable { onHotItemClick(item) }
                    .padding(vertical = 6.dp, horizontal = 10.dp),
                color = hotSearchColorList[index]
            )
        }
    }
}
