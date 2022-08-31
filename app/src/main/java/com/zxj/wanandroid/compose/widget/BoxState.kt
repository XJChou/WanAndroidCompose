package com.zxj.wanandroid.compose.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.utils.clickableNotEffect

@Composable
fun BoxState(
    modifier: Modifier = Modifier,
    state: BoxState = rememberBoxState(),
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    loading: @Composable BoxScope.() -> Unit = DefaultLoading,
    networkError: @Composable BoxScope.(() -> Unit) -> Unit = remember { defaultNetworkError() },
    fetchError: @Composable BoxScope.(() -> Unit) -> Unit = remember { defaultFetchError() },
    empty: @Composable BoxScope.() -> Unit = remember { empty() },
    success: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier,
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
    ) {
        when (val value = state.value) {
            BoxState.State.Loading -> {
                loading()
            }
            BoxState.State.Success -> {
                success()
            }
            is BoxState.State.NetworkError -> {
                networkError(value.onRefresh)
            }
            BoxState.State.Empty -> {
                empty()
            }
            is BoxState.State.FetchError -> {
                fetchError(value.onRefresh)
            }
        }
    }
}


val DefaultLoading: @Composable BoxScope.() -> Unit = {
    LoadingWheel(
        contentDesc = "加载中",
        modifier = Modifier
            .size(50.dp)
            .align(Alignment.Center)
    )
}

fun defaultFetchError(): @Composable BoxScope.(() -> Unit) -> Unit {
    return {
        Column(
            modifier = Modifier
                .clickableNotEffect(onClick = it)
                .fillMaxSize(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.no_data),
                modifier = Modifier.padding(top = 10.dp),
                color = WanAndroidTheme.colors.commonColor
            )
        }
    }
}

fun defaultNetworkError(): @Composable BoxScope.(() -> Unit) -> Unit {
    return {
        Text(
            text = stringResource(id = R.string.no_network_tip),
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxSize(1f)
                .clickableNotEffect(onClick = it)
                .align(Alignment.Center)
                .wrapContentHeight(Alignment.CenterVertically),
            color = WanAndroidTheme.colors.commonColor,
            textAlign = TextAlign.Center
        )
    }
}

fun empty(): @Composable BoxScope.() -> Unit {
    return {
        EmptyData(modifier = Modifier.align(Alignment.Center))
    }
}

@Stable
class BoxState {
    var value by mutableStateOf<State>(State.Success)

    sealed interface State {
        object Empty : State
        object Loading : State
        object Success : State
        data class NetworkError(val onRefresh: () -> Unit) : State
        data class FetchError(val onRefresh: () -> Unit) : State
    }
}

@Composable
fun rememberBoxState(): BoxState = remember {
    BoxState()
}

@Preview
@Composable
fun PreviewDefaultFetchError() {
    val state = rememberBoxState().also {
        it.value = BoxState.State.FetchError({})
    }
    BoxState(modifier = Modifier.fillMaxSize(), state) {

    }
}

@Preview
@Composable
fun PreviewDefaultNetworkError() {
    val state = rememberBoxState().also {
        it.value = BoxState.State.NetworkError({})
    }
    BoxState(modifier = Modifier.fillMaxSize(), state) {

    }
}

@Preview
@Composable
fun PreviewDefaultLoading() {
    val state = rememberBoxState().also {
        it.value = BoxState.State.Loading
    }
    BoxState(modifier = Modifier.fillMaxSize(), state) {

    }
}

@Preview
@Composable
fun PreviewDefaultEmpty() {
    val state = rememberBoxState().also {
        it.value = BoxState.State.Empty
    }
    BoxState(modifier = Modifier.fillMaxSize(), state) {

    }
}
