package com.zxj.collect.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.zxj.collect.CollectRoute

const val collectRoute = "collectRoute"

fun NavController.navigateToCollect() {
    navigate(collectRoute)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.collectScreen(
    onBack: () -> Unit,
    onBrowser: (String) -> Unit
) {
    composable(collectRoute) {
        CollectRoute(
            modifier = Modifier.fillMaxSize(),
            onBack = onBack,
            onItemClick = { onBrowser(it.link) }
        )
    }
}