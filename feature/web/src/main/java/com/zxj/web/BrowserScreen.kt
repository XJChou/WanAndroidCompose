package com.zxj.web

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.ui.TextToolBar
import com.zxj.ui.ToolBarIcon
import com.zxj.ui.R.drawable as UIDrawable

@Composable
fun BrowserRoute(
    modifier: Modifier = Modifier,
    viewModel: BrowserViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    BrowserScreen(
        modifier = modifier,
        url = viewModel.url,
        onBack = onBack
    )
}

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserScreen(
    url: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    val webState = rememberWebViewState(url)
    Scaffold(
        modifier = modifier,
        topBar = {
            TextToolBar(
                title = webState.pageTitle ?: "",
                navigationIcon = { ToolBarIcon(UIDrawable.ic_back, onBack) }
            )
        }
    ) {
        WebView(
            state = webState,
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            onCreated = {
                it.settings.javaScriptEnabled = true
                it.webChromeClient = WebChromeClient()
                it.webViewClient = WebViewClient()
            }
        )
    }
}

@Preview
@Composable
private fun PreviewBrowserScreen() {
    WanAndroidTheme {
        BrowserScreen(
            url = "https://www.baidu.com",
            onBack = {}
        )
    }
}