package com.zxj.wanandroid.compose.widget

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import kotlin.math.absoluteValue


private const val DragMultiplier = 0.5f

@Composable
fun rememberSwipeRefreshLoadState(
    isRefreshing: Boolean = false,
    isLoading: Boolean = false,
    isLoadFinish: Boolean = false
): SwipeRefreshLoadState {
    return remember {
        SwipeRefreshLoadState(
            isRefreshing = isRefreshing,
            isLoading = isLoading,
            isLoadFinish = isLoadFinish
        )
    }.also {
        it.isRefreshing = isRefreshing
        it.isLoading = isLoading
        it.isLoadFinish = isLoadFinish
    }
}

/**
 * 当前刷新加载控件状态
 *
 * 1. 当前是否刷新
 * 2. 当前是否加载
 * 3. 当前是否加载全部
 */
@Stable
class SwipeRefreshLoadState(isRefreshing: Boolean, isLoading: Boolean, isLoadFinish: Boolean) {

    private val mutatorMutex = androidx.compose.foundation.MutatorMutex()

    // 当前内容偏移
    private val _contentOffset = mutableStateOf(0f)
    val contentOffset: Float get() = _contentOffset.value

    // 当前是否拖拽
    var isSwipeInProgress: Boolean by mutableStateOf(false)
        internal set

    // 刷新相关的状态
    var isRefreshing: Boolean by mutableStateOf(isRefreshing)

    // 加载相关的状态
    var isLoading: Boolean by mutableStateOf(isLoading)
        internal set
    var isLoadFinish: Boolean by mutableStateOf(isLoadFinish)
        internal set

    internal suspend fun animateOffsetTo(offset: Float) {
        mutatorMutex.mutate {
            AnimationState(initialValue = contentOffset).animateTo(offset) {
                _contentOffset.value = this.value
            }
        }
    }

    internal suspend fun animateDecay(
        range: ClosedFloatingPointRange<Float>,
        target: Float,
        velocity: Velocity
    ): Velocity {
        // reset Data
        var endVelocity = 0f

        mutatorMutex.mutate {
            AnimationState(
                initialValue = _contentOffset.value,
                initialVelocity = velocity.y
            ).animateDecay(exponentialDecay(frictionMultiplier = 2.5f)) {
                if (value !in range) {
                    cancelAnimation()
                    endVelocity = this.velocity
                    _contentOffset.value = target
                } else {
                    _contentOffset.value = value
                }
            }
        }
        // 返回消费的
        println("origin = ${velocity.y}, remind = ${endVelocity}, comsumed = ${velocity.y - endVelocity}")
        return velocity.copy(y = velocity.y - endVelocity)
    }

    internal suspend fun snapDeltaTo(delta: Float) {
        mutatorMutex.mutate(androidx.compose.foundation.MutatePriority.UserInput) {
            _contentOffset.value += delta
        }
    }
}


/**
 * 刷新加载控件
 */
@Composable
fun SwipeRefreshLoad(
    state: SwipeRefreshLoadState,
    onRefresh: () -> Unit,
    onLoad: () -> Unit,
    modifier: Modifier = Modifier,
    swipeRefreshEnable: Boolean = true,
    swipeLoadEnable: Boolean = true,
    refreshIndicator: @Composable (SwipeRefreshLoadState) -> Unit = {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {

        }
    },
    refreshHeight: Dp = 120.dp,
    loadIndicator: @Composable (SwipeRefreshLoadState) -> Unit = {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {

        }
    },
    loadHeight: Dp = 60.dp,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val updatedOnRefresh by rememberUpdatedState(onRefresh)
    val updatedOnLoad by rememberUpdatedState(onLoad)

    // step1: 如果当前拖拽 + 当前刷新头显示
    LaunchedEffect(state.isSwipeInProgress) {
        if (!state.isSwipeInProgress && state.contentOffset > 0) {
            state.animateOffsetTo(0f)
        }
    }

    // step2: 嵌套滑动处理
    val nestedScrollConnection = remember(state, coroutineScope) {
        SwipeRefreshLoadNestedScrollConnect(
            state = state,
            coroutineScope = coroutineScope,
            onRefresh = updatedOnRefresh,
            onLoad = updatedOnLoad
        )
    }.apply {
        this.refreshEnabled = swipeRefreshEnable
        this.refreshTrigger = refreshHeight.value

        this.loadEnable = swipeLoadEnable
        this.loadTrigger = loadHeight.value
    }

    // step3: 刷新头和加载头的显示
    Box(
        modifier
            .nestedScroll(connection = nestedScrollConnection)
            .offset(0.dp, state.contentOffset.dp)
    ) {
        // 刷新头
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(refreshHeight)
                .offset(0.dp, -refreshHeight)
        ) {
            refreshIndicator(state)
        }

        // 内容
        content()

        // 加载
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .align(Alignment.BottomEnd)
                .height(loadHeight)
                .offset(0.dp, loadHeight)
        ) {
            loadIndicator(state)
        }
    }
}

/**
 * 关于刷新部分：
 * 1. onPreScroll: 如果当前刷新头已经显示且是上拉的时候，则优先处理刷新头关闭
 * 2. onPostScroll: 如果子view已经滑动完成且是下拉的时候，则处理刷新内容的显示
 * 3. onPreFling: 用户刚松开手，判断当前是否能触发刷新回调
 * 4. onPostFling: 刷新部分不响应此回调
 *
 * 关于加载部分：
 * 1. onPreScroll: 如果加载内容显示且是下拉的时候，则优先处理加载头内容关闭
 * 2. onPostScroll: 如果子view已经滑动完成且是上拉的时候，则处理加载内容的显示
 * 3. onPreFling: 如果当前加载头已经显示的且是下滑，则优先关闭加载头的内容
 * 4. onPostFling: 加载部分如果显示则触发加载内容
 */
private class SwipeRefreshLoadNestedScrollConnect(
    private val state: SwipeRefreshLoadState,
    private val coroutineScope: CoroutineScope,
    private val onRefresh: () -> Unit,
    private val onLoad: () -> Unit
) : NestedScrollConnection {

    var refreshEnabled: Boolean = false
    var refreshTrigger: Float = 0f

    var loadEnable: Boolean = false
    var loadTrigger: Float = 0f

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset = when {
        // 如果在刷新状态中不处理操作任何
        state.isRefreshing || source != NestedScrollSource.Drag -> Offset.Zero
        // 刷新头显示 + 当前上滑
        state.contentOffset > 0 && available.y < 0 -> {
            handleRefreshScroll(available)
        }
        // 加载显示的时候 + 当前是下拉
        state.contentOffset < 0 && available.y > 0 -> {
            handleLoadScroll(available)
        }
        // 其余情况不消耗
        else -> Offset.Zero
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset = when {
        // 如果在加载或者在刷新状态中
        state.isRefreshing || state.isLoading || source != NestedScrollSource.Drag -> Offset.Zero
        // 具有刷新能力 + 下滑过程
        refreshEnabled && available.y > 0 -> {
            handleRefreshScroll(available)
        }
        // 具有加载能力 + 拖拽过程 + 上拉过程
        loadEnable && available.y < 0 -> {
            handleLoadScroll(available)
        }
        else -> Offset.Zero
    }

    private fun handleRefreshScroll(available: Offset): Offset {
        state.isSwipeInProgress = true
        val newOffset = (available.y * DragMultiplier + state.contentOffset).coerceAtLeast(0f)
        val dragConsumed = newOffset - state.contentOffset
        return if (dragConsumed.absoluteValue >= 0.5f) {
            coroutineScope.launch { state.snapDeltaTo(dragConsumed) }
            Offset(x = 0f, y = dragConsumed / DragMultiplier)
        } else {
            Offset.Zero
        }
    }

    private fun handleLoadScroll(available: Offset): Offset {
        state.isSwipeInProgress = true
        val newOffset = (available.y * DragMultiplier + state.contentOffset)
            .coerceAtLeast(-loadTrigger)
            .coerceAtMost(0f)
        val dragConsumed = newOffset - state.contentOffset
        return if (dragConsumed.absoluteValue >= 0.5f) {
            coroutineScope.launch { state.snapDeltaTo(dragConsumed) }
            Offset(x = 0f, y = dragConsumed / DragMultiplier)
        } else {
            Offset.Zero
        }
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        // Reset the drag in progress state
        state.isSwipeInProgress = false

        // 加载显示 + 快滑下来
        if (state.contentOffset < 0 && available.y > 0) {
            return state.animateDecay((-60.dp.value..0f), 0f, available)
        }
        return Velocity.Zero
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        // 如果当前 available.y < 0, 则直接处理偏移值
        if (available.y < 0) {
            state.animateDecay((-60.dp.value..0f), -60.dp.value, available)
            return Velocity.Zero
        }
        return Velocity.Zero
    }
}


@Preview
@Composable
fun PreviewSwipeRefreshLoad() {
    val text = remember {
        val sb = StringBuilder()
        for (i in 0 until 1000) {
            sb.append("${i}")
        }
        sb.toString()
    }
    val scope = rememberCoroutineScope()
    val state = rememberSwipeRefreshLoadState(isRefreshing = false, isLoading = false)
    SwipeRefreshLoad(
        state = state,
        onRefresh = {
            state.isRefreshing = true
            scope.launch {
                delay(3000)
                state.isRefreshing = false
            }
        },
        onLoad = {
            state.isLoading = true
            scope.launch {
                delay(3000)
                state.isLoading = false
            }
        },
        modifier = Modifier
    ) {
        Text(
            text = text,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .background(Color.Cyan)
        )
    }
}