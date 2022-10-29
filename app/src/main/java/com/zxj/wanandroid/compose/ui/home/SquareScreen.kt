package com.zxj.wanandroid.compose.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.zxj.wanandroid.compose.Screen
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

fun NavGraphBuilder.addHomeSquare(controller: NavHostController) {
    composable(Screen.HomeSquare.route) {
        SquareRoute(
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun SquareRoute(
    modifier: Modifier = Modifier
) {
    SquareScreen(modifier)
}

@Composable
fun SquareScreen(modifier: Modifier = Modifier) {
    Text(
        text = "广场",
        modifier = modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun previewSquareScreen() {
    WanAndroidTheme {
        SquareScreen()
    }
}