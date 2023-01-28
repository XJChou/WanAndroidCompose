package com.zxj.ui.custom

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.layout.LookaheadLayoutScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import kotlinx.coroutines.launch

val colors = listOf(
    Color(0xffff6f69), Color(0xffffcc5c), Color(0xff264653), Color(0xff2a9d84)
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GoogleLookaheadLayout() {

    // Creates movable content containing 4 boxes. They will be put either in a [Row] or in a
    // [Column] depending on the state
    val items = remember {
        movableContentWithReceiverOf<LookaheadLayoutScope> {
            colors.forEach { color ->
                Box(
                    Modifier
                        .padding(15.dp)
                        .size(100.dp, 80.dp)
                        .animatePlacementInScope(this)
                        .background(color, RoundedCornerShape(20))
                )
            }
        }
    }

    var isInColumn by remember { mutableStateOf(true) }
    LookaheadLayout(
        content = {
            // As the items get moved between Column and Row, their positions in LookaheadLayout
            // will change. The `animatePlacementInScope` modifier created above will
            // observe that final position change via `localLookaheadPositionOf`, and create
            // a position animation.
            if (isInColumn) {
                Column(Modifier.fillMaxSize()) {
                    items()
                }
            } else {
                Row { items() }
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .clickable { isInColumn = !isInColumn }
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        val maxWidth: Int = placeables.maxOf { it.width }
        val maxHeight = placeables.maxOf { it.height }
        // Position the children.
        layout(maxWidth, maxHeight) {
            placeables.forEach {
                it.place(0, 0)
            }
        }
    }
}

// Creates a custom modifier to animate the local position of the layout within the
// LookaheadLayout, whenever there's a change in the layout.
@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.animatePlacementInScope(lookaheadScope: LookaheadLayoutScope) = composed {
    // 偏移动画
    var offsetAnimation by remember {
        mutableStateOf<Animatable<IntOffset, AnimationVector2D>?>(null)
    }
    // 位置偏移
    var placementOffset by remember { mutableStateOf(IntOffset.Zero) }
    // 目标偏移
    var targetOffset by remember { mutableStateOf<IntOffset?>(null) }

    // Create a `LaunchEffect` to handle target size change. This avoids creating side effects
    // from measure/layout phase.
    LaunchedEffect(Unit) {
        snapshotFlow { targetOffset }.collect { target ->
            if (target != null && target != offsetAnimation?.targetValue) {
                offsetAnimation
                    // 当前 animatable 存在这直接运行
                    ?.run { launch { animateTo(target) } }
                // 反之则创建一个Animatable
                    ?: Animatable(target, IntOffset.VectorConverter).let {
                        offsetAnimation = it
                    }
            }
        }
    }

    with(lookaheadScope) {
        this@composed
            .onPlaced { lookaheadScopeCoordinates, layoutCoordinates ->
                // This block of code has the LookaheadCoordinates of the LookaheadLayout
                // as the first parameter, and the coordinates of this modifier as the second
                // parameter.

                // localLookaheadPositionOf returns the *target* position of this
                // modifier in the LookaheadLayout's local coordinates.
                targetOffset = lookaheadScopeCoordinates
                    .localLookaheadPositionOf(layoutCoordinates)
                    .round()

                // localPositionOf returns the *current* position of this
                // modifier in the LookaheadLayout's local coordinates.
                placementOffset = lookaheadScopeCoordinates
                    .localPositionOf(layoutCoordinates, Offset.Zero)
                    .round()
            }
            // The measure logic in `intermediateLayout` is skipped in the lookahead pass, as
            // intermediateLayout is expected to produce intermediate stages of a layout
            // transform. When the measure block is invoked after lookahead pass, the lookahead
            // size of the child will be accessible as a parameter to the measure block.
            .intermediateLayout { measurable, constraints, _ ->
                val placeable = measurable.measure(constraints)
                layout(placeable.width, placeable.height) {
                    // offsetAnimation will animate the target position whenever it changes.
                    // In order to place the child at the animated position, we need to offset
                    // the child based on the target and current position in LookaheadLayout.
                    val (x, y) = offsetAnimation?.run { value - placementOffset }
                    // If offsetAnimation has not been set up yet (i.e. in the first frame),
                    // skip the animation
                        ?: (targetOffset!! - placementOffset)
                    placeable.place(x, y)
                }
            }
    }
}

@Preview
@Composable
private fun PreviewGoogleLookaheadLayout() {
    GoogleLookaheadLayout()
}