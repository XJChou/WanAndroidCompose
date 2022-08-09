package com.zxj.wanandroid.compose.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 导航子项
 * @param isBig 是否大模式
 * @param icon 当前显示图片
 * @param text 当前文本
 */
@Composable
fun NavigationItem(
    modifier: Modifier = Modifier,
    isBig: Boolean,
    @DrawableRes icon: Int,
    text: String,
    tint: Color
) {
    val textSize = animateFloatAsState(
        targetValue = if (isBig) 13.sp.value else 12.sp.value,
    )
    Column(
        modifier.fillMaxHeight(1f),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 图标
        Icon(
            painter = painterResource(id = icon),
            contentDescription = text,
            Modifier.size(20.dp),
            tint = tint
        )
        // 文本
        Text(
            text = text,
            modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 5.dp),
            fontSize = textSize.value.sp,
            color = tint
        )
    }
}

data class NavigationItemBean(@DrawableRes val icon:Int, @StringRes val title:Int)