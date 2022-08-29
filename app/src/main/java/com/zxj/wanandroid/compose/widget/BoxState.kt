package com.zxj.wanandroid.compose.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BoxState(
    modifier: Modifier = Modifier,
    state: BoxState = rememberBoxState(),
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    success: @Composable BoxScope.() -> Unit,
    error: @Composable BoxScope.() -> Unit,
    loading: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier,
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
    ) {

    }
}

@Stable
class BoxState {
    var state by mutableStateOf<State>(State.Success)

    sealed interface State {
        object Loading : State
        object Success : State
        object Error : State
    }
}

@Composable
fun rememberBoxState(): BoxState = remember {
    BoxState()
}


