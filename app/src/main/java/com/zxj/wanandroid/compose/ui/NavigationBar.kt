package com.zxj.wanandroid.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.getString
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

val icons = arrayOf(
    R.drawable.ic_home_black_24dp,
    R.drawable.ic_square_black_24dp,
    R.drawable.ic_wechat_black_24dp,
    R.drawable.ic_apps_black_24dp,
    R.drawable.ic_project_black_24dp
)

val texts = arrayOf(
    R.string.navigation_text_home,
    R.string.navigation_text_square,
    R.string.navigation_text_public,
    R.string.navigation_text_system,
    R.string.navigation_text_project
)

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    selectIndex: Int,
    onItemSelectedChanged: ((Int) -> Unit)? = null
) {
    Row(
        modifier
            .background(WanAndroidTheme.colors.viewBackground)
            .fillMaxWidth()
            .height(56.dp)
    ) {
        icons.forEachIndexed { index, icon ->
            NavigationItem(
                isBig = selectIndex == index,
                icon = icon,
                text = getString(texts[index]),
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onItemSelectedChanged?.invoke(index)
                    },
                tint = if (selectIndex == index) {
                    WanAndroidTheme.colors.blueGrey
                } else {
                    WanAndroidTheme.colors.textColorPrimary
                }
            )
        }
    }
}