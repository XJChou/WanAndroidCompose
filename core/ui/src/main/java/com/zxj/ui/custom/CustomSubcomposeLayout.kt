package com.zxj.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.lang.Integer.max

/**
 * 用于将重组过程延迟到测量过程，以至于可以得到父组件的约束，用于做动态化布局
 */
@Composable
private fun CustomSubComposeLayout(
    modifier: Modifier = Modifier
) {
    BoxWithConstraints() {
        
    }
    SubcomposeLayout(modifier) { constraints ->
        val childConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        // subcompose 来布局的
        val placeable = this
            .subcompose(Unit) {
                val minWidth = with(LocalDensity.current) {
                    constraints.minWidth.toDp()
                }
                if (minWidth < 480.dp) {
                    Column() {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.Yellow)
                        )
                        Text(text = "123", modifier.padding(top = 8.dp))
                    }
                } else {
                    Row() {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.Yellow)
                        )
                        Text(text = "123", modifier.padding(start = 8.dp))
                    }
                }
            }
            .map { it.measure(childConstraints) }

        // 布局宽高
        val layoutWidth = max(constraints.minWidth, placeable.maxOf { it.width })
        val layoutHeight = max(constraints.minHeight, placeable.maxOf { it.height })
        layout(layoutWidth, layoutHeight) {
            placeable.forEach { it.placeRelative(0, 0) }
        }
    }
}

@Preview
@Composable
private fun PreviewCustomSubComposeLayout() {
    CustomSubComposeLayout(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow.copy(0.1f)))
}