package com.zxj.wanandroid.compose.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme

val TOOLBAR_HEIGHT = 48.dp

@Composable
fun TextToolBar(
    title: String,
    fitsSystemWindows: Boolean = true,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    var modifier: Modifier = Modifier.background(WanAndroidTheme.colors.colorPrimary)
    if (fitsSystemWindows) modifier = modifier.statusBarsPadding()
    modifier = modifier
        .fillMaxWidth(1f)
        .height(TOOLBAR_HEIGHT)
    Toolbar(
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                color = WanAndroidTheme.colors.itemTagTv,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        navigationIcon,
        actions
    )
}

@Composable
fun ToolBarIcon(@DrawableRes drawableRes: Int, action: () -> Unit) {
    Icon(
        painter = painterResource(id = drawableRes),
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier
            .size(24.dp)
            .clickable(
                onClick = action,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    )
}

@Composable
fun SearchToolBar(
    state: SearchState,
    hint: String,
    fitsSystemWindows: Boolean = true,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    onSearch: (String) -> Unit,
) {
    var modifier: Modifier = Modifier.background(WanAndroidTheme.colors.colorPrimary)
    if (fitsSystemWindows) modifier = modifier.statusBarsPadding()
    modifier = modifier
        .fillMaxWidth(1f)
        .height(TOOLBAR_HEIGHT)
    Toolbar(
        title = {
            BasicTextField(
                singleLine = true,
                value = state.value,
                onValueChange = {
                    state.value = it
                },
                textStyle = TextStyle(
                    color = WanAndroidTheme.colors.itemTagTv,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start
                ),
                decorationBox = {
                    if (state.value.isEmpty()) {
                        Text(
                            text = hint,
                            color = WanAndroidTheme.colors.itemTagTv.copy(0.5f),
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start
                        )
                    }
                    it()
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions { onSearch(state.value) },
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier,
        navigationIcon,
        actions
    )
}

@Composable
fun rememberSearchState(value: String): SearchState {
    return remember { SearchState(value) }
}

class SearchState(value: String) {
    var value by mutableStateOf(value)
}

@Composable
private fun Toolbar(
    title: @Composable () -> Unit,
    modifier: Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigationIcon?.also {
            Spacer(modifier = Modifier.width(10.dp))
            it()
        }

        Box(
            Modifier
                .padding(horizontal = 15.dp)
                .weight(1f)
        ) {
            title()
        }

        if (actions != null) {
            actions()
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Preview
@Composable
fun PreviewToolbar() {
    TextToolBar(
        title = "测试",
        navigationIcon = {
            ToolBarIcon(drawableRes = R.drawable.ic_back) {

            }
        },
        actions = {
            ToolBarIcon(drawableRes = R.drawable.ic_search_white_24dp) {

            }
        }
    )
}

@Preview
@Composable
fun PreviewSearchToolbar() {
    SearchToolBar(
        rememberSearchState(value = ""),
        stringResource(id = R.string.search_tint),
        true,
        navigationIcon = {
            ToolBarIcon(drawableRes = R.drawable.ic_back) {

            }
        },
        actions = {
            ToolBarIcon(drawableRes = R.drawable.ic_search_white_24dp) {

            }
        }
    ) {}
}