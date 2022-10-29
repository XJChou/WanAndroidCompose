package com.zxj.wanandroid.compose.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.data.bean.User
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.utils.clickableNotEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDrawer(
    isLogin: Boolean,
    user: User,
    modifier: Modifier = Modifier,
    onMineCollect: () -> Unit,
    onMineScore: () -> Unit,
    onShareClick: () -> Unit,
    onRankClick: () -> Unit,
    onTodoClick: () -> Unit,
    onSettingClick: () -> Unit,
    onSignOut: () -> Unit,
) {

    ModalDrawerSheet(
        modifier = modifier,
        drawerShape = RectangleShape,
        windowInsets = WindowInsets(0.dp),
        drawerContainerColor = WanAndroidTheme.colors.viewBackground
    ) {

        // 状态栏
        Spacer(
            modifier = Modifier
                .background(WanAndroidTheme.colors.colorPrimary)
                .systemBarsPadding()
                .fillMaxWidth()
        )

        // 头部
        DrawerHeader(
            isLogin = isLogin,
            user = user,
            onRankClick = onRankClick
        )

        DrawerItem(
            icon = R.drawable.ic_score_white_24dp,
            title = stringResource(id = R.string.nav_my_score),
            onItemClick = onMineScore
        )
        DrawerItem(
            icon = R.drawable.ic_like_not,
            title = stringResource(id = R.string.nav_my_collect),
            onItemClick = onMineCollect
        )
        DrawerItem(
            icon = R.drawable.ic_share_white_24dp,
            title = stringResource(id = R.string.my_share),
            onItemClick = onShareClick
        )
        DrawerItem(
            icon = R.drawable.ic_todo_default_24dp,
            title = stringResource(id = R.string.nav_todo),
            onItemClick = onTodoClick
        )
        DrawerItem(
            icon = R.drawable.ic_night_24dp,
            title = stringResource(id = R.string.nav_night_mode),
            onItemClick = WanAndroidTheme::changeTheme
        )
        DrawerItem(
            icon = R.drawable.ic_setting_24dp,
            title = stringResource(id = R.string.nav_setting),
            onItemClick = onSettingClick
        )
        if (isLogin) {
            DrawerItem(
                icon = R.drawable.ic_logout_white_24dp,
                title = stringResource(id = R.string.nav_logout),
                onItemClick = onSignOut
            )
        }
    }
}

@Composable
private fun DrawerHeader(
    isLogin: Boolean,
    user: User,
    onRankClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(WanAndroidTheme.colors.colorPrimary)
            .fillMaxWidth()
            .padding(16.dp, 10.dp, 16.dp, 10.dp),
    ) {
        // 排名
        Icon(
            painter = painterResource(id = R.drawable.ic_rank_white_24dp),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.End)
                .clickableNotEffect(onClick = onRankClick),
            tint = Color.White
        )

        // 圆形图边
        AsyncImage(
            model = "",
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .border(2.dp, Color.White, CircleShape)
                .align(Alignment.CenterHorizontally)
        )

        // 根据当前状态
        Text(
            text = if (isLogin) user.username else stringResource(R.string.go_login),
            modifier = Modifier
                .padding(0.dp, 12.dp, 0.dp, 0.dp)
                .align(Alignment.CenterHorizontally),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .padding(0.dp, 8.dp, 0.dp, 0.dp)
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.nav_grade),
                color = Color(0xFFF5F5F5),
                fontSize = 12.sp
            )
            Text(
                text = stringResource(id = R.string.nav_line_2),
                fontSize = 12.sp,
                color = Color(0xFFF5F5F5),
            )
            Text(
                text = stringResource(id = R.string.nav_rank),
                modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp),
                fontSize = 12.sp,
                color = Color(0xFFF5F5F5),
            )
            Text(
                text = stringResource(id = R.string.nav_line_2),
                fontSize = 12.sp,
                color = Color(0xFFF5F5F5),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawerItem(@DrawableRes icon: Int, title: String, onItemClick: () -> Unit) {
    NavigationDrawerItem(
        icon = {
            androidx.compose.material3.Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
            )
        },
        label = {
            androidx.compose.material3.Text(text = title)
        },
        shape = RectangleShape,
        colors = NavigationDrawerItemDefaults.colors(
            unselectedTextColor = WanAndroidTheme.colors.itemNavColorTv,
            unselectedIconColor = WanAndroidTheme.colors.itemNavColorTv,
            unselectedContainerColor = WanAndroidTheme.colors.viewBackground
        ),
        selected = false,
        onClick = onItemClick
    )
}


@Preview
@Composable
fun PreviewHomeDrawer() {
    WanAndroidTheme {
        HomeDrawer(
            isLogin = false,
            user = User(),
            onRankClick = { },
            onSignOut = {},
            onMineCollect = {},
            onMineScore = {},
            onShareClick = {},
            onSettingClick = {},
            onTodoClick = {}
        )
    }
}