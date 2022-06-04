package com.zxj.wanandroid.compose.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    leftControl: List<ControlBean>? = null,
    centerControl: @Composable (() -> Unit),
    rightControl: List<ControlBean>? = null
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .background(WanAndroidTheme.colors.colorPrimary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leftControl != null) {
            Row {
                leftControl.forEach { item ->
                    ControlItem(
                        item = item,
                        Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                    )
                }
            }
        }

        Box(
            Modifier
                .padding(10.dp, 0.dp)
                .weight(1f)
        ) {
            centerControl()
        }

        // 右控制域
        if (rightControl != null) {
            Row {
                rightControl.forEach { item ->
                    ControlItem(
                        item = item,
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 10.dp, 0.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ControlItem(item: ControlBean, modifier: Modifier) {
    Icon(
        painter = painterResource(id = item.icon),
        contentDescription = item.contentDescription,
        tint = item.tiny,
        modifier = modifier
            .size(24.dp)
            .clickable { item.onClick?.invoke() }
    )
}

/**
 * Toolbar控制单元
 */
class ControlBean(
    @DrawableRes val icon: Int,
    val tiny: Color = Color.White,
    val contentDescription: String = "",
    val onClick: (() -> Unit)? = null
)

@Preview
@Composable
fun PreviewToolbar() {
    Toolbar(
        modifier = Modifier.fillMaxWidth(),
        arrayListOf(
            ControlBean(icon = R.drawable.ic_menu_white_24dp)
        ), {
            Text(
                text = "测试",
                fontSize = 18.sp,
                color = WanAndroidTheme.colors.itemTagTv
            )
        },
        arrayListOf(
            ControlBean(icon = R.drawable.ic_search_white_24dp)
        )
    )
}