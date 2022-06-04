package com.zxj.wanandroid.compose.ui.page.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.ui.ControlBean
import com.zxj.wanandroid.compose.ui.Toolbar
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

@Composable
fun Search(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Toolbar(
            modifier = Modifier.fillMaxWidth(),
            leftControl = arrayListOf(ControlBean(R.drawable.ic_back, contentDescription = "返回") {
                navController.popBackStack()
            }),
            centerControl = {
                BasicTextField(
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    value = "value",
                    onValueChange = {

                    })
            },
            rightControl = arrayListOf(
                ControlBean(
                    R.drawable.ic_search,
                    contentDescription = "搜索"
                ) {
                    toast("搜索")
                })
        )
    }
}

@Preview
@Composable
fun SearchPrev() {
    Search(rememberNavController())
}