package com.zxj.web.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zxj.web.BrowserRoute

const val browserRoute = "browser"

// 参数
private val browserArgs = "browserArgs"

internal class BrowserArgs private constructor(val url: String) {
    constructor(stateHandle: SavedStateHandle) : this(
        Uri.decode(stateHandle[browserArgs])
    )
}

fun NavController.navigateToBrowser(url: String) {
    val encodeURL = Uri.encode(url)
    navigate("$browserRoute/${encodeURL}")
}

fun NavGraphBuilder.browserScreen(onBack: () -> Unit) {
    composable(
        "$browserRoute/$browserArgs",
        listOf(navArgument(browserArgs) { type = NavType.StringType })
    ) {
        BrowserRoute(
            modifier = Modifier.fillMaxSize(),
            onBack = onBack
        )
    }
}