package com.zxj.ui.custom

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.layout.LookaheadLayoutCoordinates
import androidx.compose.ui.layout.LookaheadLayoutScope
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SimpleLookaheadLayout(
    content: @Composable LookaheadLayoutScope.() -> Unit,
) {
    LookaheadLayout(content = content, modifier = Modifier.fillMaxSize()) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        val width = placeables.maxOf { it.width }
        val height = placeables.maxOf { it.height }
        layout(width, height) {
            placeables.forEach { it.placeRelative(0, 0) }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun PreviewSimpleLookaheadLayout() {
    var isTextHeight200Dp by remember { mutableStateOf(false) }

    var textHeightPx by remember { mutableStateOf(0) }
    val textHeightPxAnim by animateIntAsState(targetValue = textHeightPx)

    var lookaheadOffset by remember { mutableStateOf(Offset.Zero) }
    val lookaheadOffsetAnim by animateOffsetAsState(targetValue = lookaheadOffset)

    val sharedText = remember {
        movableContentWithReceiverOf<LookaheadLayoutScope> {
            Text(
                text = "扔物线",
                modifier = Modifier
                    .then(if (isTextHeight200Dp) Modifier.padding(50.dp) else Modifier)
                    .onPlaced { lookaheadScopeCoordinates: LookaheadLayoutCoordinates, layoutCoordinates: LookaheadLayoutCoordinates ->
                        lookaheadOffset =
                            lookaheadScopeCoordinates.localLookaheadPositionOf(layoutCoordinates)
                    }
                    .intermediateLayout { measurable, constraints, lookaheadSize ->
                        textHeightPx = lookaheadSize.height
                        val placeable = measurable.measure(
                            Constraints.fixed(lookaheadSize.width, textHeightPxAnim)
                        )
//                        val placeable = measurable.measure(constraints)
                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(
                                (lookaheadOffsetAnim - lookaheadOffset).x.roundToInt(),
                                (lookaheadOffsetAnim - lookaheadOffset).y.roundToInt(),
                            )
                        }
                    }
                    .then(if (isTextHeight200Dp) Modifier.height(200.dp) else Modifier)
                    .clickable { isTextHeight200Dp = !isTextHeight200Dp }
            )
        }
    }
    SimpleLookaheadLayout {
        if (isTextHeight200Dp) {
            Row() {
                Text(text = "rengwuxian")
                sharedText()
            }
        } else {
            Column() {
                Text(text = "rengwuxian")
                sharedText()
            }
        }
    }
}