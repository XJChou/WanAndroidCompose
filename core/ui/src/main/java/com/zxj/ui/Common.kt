package com.zxj.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import com.zxj.designsystem.theme.WanAndroidTheme

fun Modifier.itemDivider(): Modifier = this.composed {
    val color = WanAndroidTheme.colors.listDivider
    this.drawBehind {
        drawLine(
            color = color,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height)
        )
    }
}