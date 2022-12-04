package com.zxj.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zxj.designsystem.theme.WanAndroidTheme

private val showInitial = @Composable {

}

private val showLoading = @Composable {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = WanAndroidTheme.colors.colorPrimary
        )
    }
}

private val showError = @Composable { msg: String ->
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = null
        )

        Text(
            text = msg,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

/**
 * 状态显示Composable
 */
@Composable
fun MultipleStatus(
    status: Status,
    modifier: Modifier = Modifier,
    initial: @Composable () -> Unit = showInitial,
    loading: @Composable () -> Unit = showLoading,
    error: @Composable (msg: String) -> Unit = showError,
    retry: () -> Unit,
    content: @Composable Status.Success.() -> Unit,
) {
    Box(modifier) {
        when (status) {
            Status.Initial -> {
                Box(modifier = Modifier.matchParentSize()) {
                    initial()
                }
            }
            Status.Loading -> {
                Box(modifier = Modifier.matchParentSize()) {
                    loading()
                }
            }
            is Status.Success -> {
                Box(modifier = Modifier.matchParentSize()) {
                    status.content()
                }
            }
            is Status.Error -> {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable(onClick = retry)
                ) {
                    error(status.msg)
                }
            }
        }

    }
}

@Preview
@Composable
private fun PreviewMultipleStatus() {
    WanAndroidTheme {
        MultipleStatus(
            status = Status.Loading,
            modifier = Modifier.fillMaxSize(),
            retry = {}
        ) {

        }
    }
}


sealed interface Status {
    object Initial : Status
    object Loading : Status

    class Success(private val data: Any?) : Status {

        @Suppress("UNCHECKED_CAST")
        fun <Data> getData(): Data = data as Data
    }

    class Error(val msg: String) : Status
}