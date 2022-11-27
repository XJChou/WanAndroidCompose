package com.zxj.article.project

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign


@Composable
internal fun ProjectRoute(
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit
) {
    ProjectScreen(
        modifier = Modifier,
        navigateToBrowser = navigateToBrowser
    )
}

@Composable
private fun ProjectScreen(
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit
) {
    Text(
        text = "项目",
        modifier = modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}