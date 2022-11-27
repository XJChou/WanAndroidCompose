package com.zxj.share.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zxj.share.ShareRoute

const val shareRoute = "shareRoute"

fun NavController.navigateToShare() {
    navigate(shareRoute)
}

fun NavGraphBuilder.shareScreen(
    onBack: () -> Unit,
    onBrowser: (String) -> Unit
) {
    composable(shareRoute) {
        ShareRoute(
            onBack = onBack,
            onBrowser = onBrowser
        )
    }
}