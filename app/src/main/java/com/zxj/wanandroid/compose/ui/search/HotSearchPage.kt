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
import java.util.*

@Composable
fun ColumnScope.HotSearchPage(hotSearchList: List<HotSearchBean>, onHotItemClick: (HotSearchBean) -> Unit) {
    Text(
        text = GetString(id = R.string.hot_search),
        color = WanAndroidTheme.colors.colorAccent,
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    FlowRow(modifier = Modifier.fillMaxWidth(1f)) {
        hotSearchList.forEach {
            Text(
                text = it.name,
                modifier = Modifier
                    .padding(5.dp)
                    .background(Color(0xFFEEEEEE), Shapes.medium)
                    .clickable { onHotItemClick(it) }
                    .padding(vertical = 6.dp, horizontal = 10.dp),
                color = Color(RandomColor())
            )
        }
    }
}

@Composable
private fun RandomColor(): Int {
    val random = Random()
    //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
    var red = random.nextInt(190)
    var green = random.nextInt(190)
    var blue = random.nextInt(190)
    if (WanAndroidTheme.theme == WanAndroidTheme.Theme.Night) {
        //150-255
        red = random.nextInt(105) + 150
        green = random.nextInt(105) + 150
        blue = random.nextInt(105) + 150
    }
    //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
    return android.graphics.Color.rgb(red, green, blue)
}
