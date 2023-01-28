package com.zxj.ui.custom

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.layout.LookaheadLayoutCoordinates
import androidx.compose.ui.layout.LookaheadLayoutScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
@OptIn(ExperimentalComposeUiApi::class)
private fun CustomLookaheadLayout(
    modifier: Modifier = Modifier,
    content: @Composable @UiComposable LookaheadLayoutScope.() -> Unit
) {
    LookaheadLayout(
        content = content,
        modifier = modifier,
    ) { measurables, constaints ->
        val childConstaints = constaints.copy(minWidth = 0, minHeight = 0)
        val placeables = measurables.map { it.measure(childConstaints) }
        val maxWidth = placeables.maxOf { it.width }
        val maxHeight = placeables.maxOf { it.height }
        val layoutWidth = max(constaints.minWidth, maxWidth)
        val layoutHeight = max(constaints.minHeight, maxHeight)
        layout(layoutWidth, layoutHeight) {
            placeables.forEach {
                it.placeRelative(0, 0)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
private fun PreviewCustomLookaheadLayout() {
    var switch by remember { mutableStateOf(false) }
    val state = remember { LookaheadState() }
    CustomLookaheadLayout(modifier = Modifier.fillMaxSize()) {
        if (switch) {
            Column(modifier = Modifier) {
                Text(text = "我是占位", modifier = Modifier)
                MovableText(state, "测试", Modifier.background(Color.Cyan).clickable { switch = !switch })
            }
        } else {
            Row(modifier = Modifier) {
                Text(text = "我是占位", modifier = Modifier)
                MovableText(state, "测试",
                    Modifier.clickable { switch = !switch }.size(80.dp).background(Color.Cyan))
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun LookaheadLayoutScope.MovableText(
    state: LookaheadState,
    text: String,
    modifier: Modifier = Modifier
) {
    val textContent by rememberUpdatedState(newValue = text)
    val scope = rememberCoroutineScope()

    val movableText = remember {
        movableContentWithReceiverOf<LookaheadLayoutScope> {
            Text(
                text = textContent,
                modifier = modifier
                    .onPlaced { scopeCoordinates: LookaheadLayoutCoordinates, layoutCoordinates: LookaheadLayoutCoordinates ->
                        // 相当于lookaheadScope的位置
                        scope.launch {
                            state.animateTo(
                                scopeCoordinates.localLookaheadPositionOf(layoutCoordinates)
                            )
                        }
                    }
                    .intermediateLayout { measurable, constraints, lookaheadSize ->
                        // 内部布局的宽高
                        scope.launch {
                            state.animateSizeTo(
                                IntOffset(lookaheadSize.width, lookaheadSize.height)
                            )
                        }
                        val placeable = measurable.measure(
                            Constraints.fixed(state.sizeAnim.value.x, state.sizeAnim.value.y)
                        )
                        println("size = [${placeable.width}, ${placeable.height}]")
                        // 显示当前宽高
                        layout(placeable.width, placeable.height) {
                            val deltaOffset = state.offsetAnim.value - state.offsetAnim.targetValue
                            placeable.placeRelative(
                                deltaOffset.x.roundToInt(),
                                deltaOffset.y.roundToInt()
                            )
                        }
                    }
            )
        }
    }
    movableText()
}

@Stable
class LookaheadState {

    var mutex = Mutex()

    val offsetAnim = Animatable(
        initialValue = Offset.Zero,
        typeConverter = Offset.VectorConverter,
        visibilityThreshold = null,
        label = "offset"
    )

    val sizeAnim = Animatable(
        initialValue = IntOffset.Zero,
        typeConverter = IntOffset.VectorConverter,
        visibilityThreshold = null,
        label = "size"
    )

    suspend fun animateTo(targetOffset: Offset) {
        if (offsetAnim.targetValue != targetOffset) {
            offsetAnim.animateTo(targetOffset)
        }
    }

    suspend fun animateSizeTo(sizeOffset: IntOffset) {
        if (sizeAnim.targetValue != sizeOffset) {
            sizeAnim.animateTo(sizeOffset)
        }
    }
}