package com.zxj.share.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.zxj.share.ShareArticleRoute
import com.zxj.share.ShareRoute

const val shareRoute = "shareRoute"

const val shareArticleRoute = "shareArticleRoute"

fun NavController.navigateToShare() {
    navigate(shareRoute)
}

fun NavController.navigateToShareArticle() {
    navigate(shareArticleRoute)
}

@OptIn(ExperimentalAnimationApi::class)
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

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.shareArticleScreen(
    onBack: () -> Unit
) {
    composable(shareArticleRoute) {
        ShareArticleRoute(
            modifier = Modifier.fillMaxSize(),
            onBack = onBack
        )
    }
}