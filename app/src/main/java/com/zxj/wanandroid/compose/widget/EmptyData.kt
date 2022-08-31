package com.zxj.wanandroid.compose.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

@Composable
fun EmptyData(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_empty),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.no_data),
            modifier = Modifier.padding(top = 10.dp),
            color = WanAndroidTheme.colors.commonColor
        )
    }
}

@Preview
@Composable
fun PreviewEmptyData() {
    EmptyData(modifier = Modifier)
}