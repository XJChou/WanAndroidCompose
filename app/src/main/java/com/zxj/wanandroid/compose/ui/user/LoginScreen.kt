package com.zxj.wanandroid.compose.ui.user

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.Screen
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.LoginViewAction
import com.zxj.wanandroid.compose.viewmodel.LoginViewEvent
import com.zxj.wanandroid.compose.viewmodel.LoginViewModel
import com.zxj.wanandroid.compose.widget.TextToolBar
import com.zxj.wanandroid.compose.widget.ToolBarIcon
import kotlinx.coroutines.flow.collectLatest

/**
 * 添加登录界面到Navigation
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addLoginScreen(controller: NavHostController) {
    composable(Screen.Login.route) {
        LoginScreen(
            onBack = {
                controller.popBackStack()
            },
            registerAction = {
                controller.navigate(Screen.Register.route)
            }
        )
    }
}

@Composable
fun LoginScreen(
    onBack: () -> Unit = { },
    registerAction: () -> Unit = {}
) {
    Column(Modifier.fillMaxSize()) {
        TextToolBar(
            title = stringResource(id = R.string.login),
            fitsSystemWindows = true,
            navigationIcon = {
                ToolBarIcon(drawableRes = R.drawable.ic_back, onBack)
            }
        )
        // 内容
        LoginPage(
            onRegisterClick = {
                onBack()
                registerAction()

            },
            onLoginSuccess = {
                onBack()
            }
        )
    }
}

@Composable
fun ColumnScope.LoginPage(
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val username by remember { derivedStateOf { uiState.username } }
    val password by remember { derivedStateOf { uiState.password } }

    LaunchedEffect(key1 = viewModel) {
        viewModel.uiEvent.collectLatest {
            when (it) {
                is LoginViewEvent.UserNameIsNotEmpty -> {
                    toast("用户名不能为空")
                }
                is LoginViewEvent.PasswordIsNotEmpty -> {
                    toast("密码不为为空")
                }
                is LoginViewEvent.LoginSuccess -> {
                    onLoginSuccess()
                }
                is LoginViewEvent.LoginError -> {
                    toast("登录失败：${it.message}")
                }
            }
        }
    }

    Column(
        Modifier
            .background(WanAndroidTheme.colors.viewBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp, 40.dp, 24.dp, 0.dp)
            .fillMaxWidth()
            .weight(1f)
    ) {
        Text(
            text = stringResource(R.string.user_login),
            color = WanAndroidTheme.colors.commonColor,
            fontSize = 18.sp
        )
        Text(
            text = stringResource(R.string.register_tip),
            fontSize = 14.sp,
            modifier = Modifier.padding(0.dp, 6.dp, 0.dp, 0.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(0.dp, 30.dp, 0.dp, 8.dp)
                .fillMaxWidth(),
            value = username,
            onValueChange = {
                viewModel.dispatch(LoginViewAction.InputUsernameAction(it))
            },
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.username)) }
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(0.dp, 8.dp)
                .fillMaxWidth(),
            value = password,
            singleLine = true,
            onValueChange = {
                viewModel.dispatch(LoginViewAction.InputPasswordAction(it))
            },
            label = { Text(text = stringResource(id = R.string.password)) },
            visualTransformation = PasswordVisualTransformation()
        )
        Text(
            text = stringResource(id = R.string.login),
            modifier = Modifier
                .padding(0.dp, 24.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(WanAndroidTheme.colors.colorAccent)
                .clickable { viewModel.dispatch(LoginViewAction.LoginAction) }
                .padding(12.dp),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.no_account),
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 24.dp)
                .align(Alignment.End)
                .clickable { onRegisterClick() },
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    WanAndroidTheme {
        LoginScreen()
    }
}