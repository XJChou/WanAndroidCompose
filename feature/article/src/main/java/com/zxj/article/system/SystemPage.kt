package com.zxj.article.system

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
internal fun SystemRoute(
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit
) {
    SystemScreen(
        modifier = modifier,
        navigateToBrowser = navigateToBrowser
    )
}

@Composable
private fun SystemScreen(
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit
) {
    Text(
        text = "体系",
        modifier = modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}