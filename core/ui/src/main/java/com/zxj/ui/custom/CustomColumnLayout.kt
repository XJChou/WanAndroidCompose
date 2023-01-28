package com.zxj.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.lang.Integer.max

/**
 * 用于自定义子LayoutNode布局
 */
@Composable
private fun CustomColumnLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val newConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val placeables = measurables.map {
            it.measure(newConstraints)
        }
        // Column布局
        var totalWidth = 0
        var totalHeight = 0
        placeables.forEach {
            totalWidth = Math.max(totalWidth, it.width)
            totalHeight += it.height
        }
        val layoutWidth = max(totalWidth, constraints.minWidth)
        val layoutHeight = max(totalHeight, constraints.minHeight)
        layout(layoutWidth, layoutHeight) {
            var top = 0
            placeables.forEach {
                it.placeRelative(0, top)
                top += it.height
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCustomColumnLayout() {
    CustomColumnLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow.copy(0.2f))
    ) {
        Box(
            Modifier
                .size(200.dp, 80.dp)
                .background(Color.Black.copy(0.6f))
        )
        Box(
            Modifier
                .size(200.dp, 120.dp)
                .background(Color.Blue.copy(0.6f))
        )
        Box(
            Modifier
                .size(120.dp, 80.dp)
                .background(Color.Cyan.copy(0.6f))
        )
    }
}