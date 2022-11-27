package com.zxj.article.square

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SquareRoute(
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit,
) {
    SquareScreen(
        modifier = modifier,
        navigateToBrowser = navigateToBrowser
    )
}

@Composable
fun SquareScreen(
    modifier: Modifier = Modifier,
    navigateToBrowser: (String) -> Unit,
) {
    Text(
        text = "广场",
        modifier = modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}
