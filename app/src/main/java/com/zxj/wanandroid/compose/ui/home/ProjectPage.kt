package com.zxj.wanandroid.compose.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.zxj.wanandroid.compose.Screen

fun NavGraphBuilder.addHomeProject(controller: NavHostController) {
    composable(Screen.HomeProject.route) {
        ProjectPage(
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun ProjectPage(modifier: Modifier = Modifier) {
    Text(
        text = "项目",
        modifier = modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}