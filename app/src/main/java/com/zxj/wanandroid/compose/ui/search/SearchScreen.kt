package com.zxj.wanandroid.compose.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.widget.ControlBean
import com.zxj.wanandroid.compose.widget.Toolbar

@Composable
fun SearchScreen(navController: NavController) {
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
    SearchScreen(rememberNavController())
}