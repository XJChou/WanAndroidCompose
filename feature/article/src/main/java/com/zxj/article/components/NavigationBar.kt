package com.zxj.article.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zxj.designsystem.theme.WanAndroidTheme

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    navigationItems: Array<NavigationItemBean>,
    selectIndex: Int,
    onItemSelectedChanged: ((Int) -> Unit)? = null
) {
    Row(
        modifier
            .background(WanAndroidTheme.colors.viewBackground)
            .fillMaxWidth()
            .height(56.dp)
    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationItem(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onItemSelectedChanged?.invoke(index)
                    },
                isBig = selectIndex == index,
                icon = item.icon,
                text = stringResource(item.title),
                tint = if (selectIndex == index) {
                    WanAndroidTheme.colors.blueGrey
                } else {
                    WanAndroidTheme.colors.textColorPrimary
                }
            )
        }
    }
}