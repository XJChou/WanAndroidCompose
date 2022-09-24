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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.RegisterViewAction
import com.zxj.wanandroid.compose.viewmodel.RegisterViewEvent
import com.zxj.wanandroid.compose.viewmodel.RegisterViewModel
import com.zxj.wanandroid.compose.widget.TextToolBar
import com.zxj.wanandroid.compose.widget.ToolBarIcon
import kotlinx.coroutines.flow.collectLatest


/**
 * 添加注册界面到Navigation
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addRegisterScreen(controller: NavHostController) {
    composable(NavigationRoute.REGISTER) {
        RegisterScreen(
            onBack = {
                controller.popBackStack()
            },
            loginAction = {
                controller.navigate(NavigationRoute.LOGIN)
            }
        )
    }
}


@Composable
fun RegisterScreen(
    onBack: () -> Unit = {},
    loginAction: () -> Unit = {}
) {
    Column(Modifier.fillMaxSize()) {
        TextToolBar(
            title = stringResource(R.string.register),
            fitsSystemWindows = true,
            navigationIcon = {
                ToolBarIcon(drawableRes = R.drawable.ic_back) {
                    onBack()
                }
            }
        )
        // 内容
        RegisterPage(
            onLoginClick = {
                onBack()
                loginAction()
            },
            onRegisterSuccess = {
                onBack()
            }
        )
    }
}

@Composable
fun ColumnScope.RegisterPage(
    onLoginClick: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val viewModel: RegisterViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = viewModel) {
        viewModel.uiEvent.collectLatest {
            when (it) {
                is RegisterViewEvent.UserNameIsNotEmpty -> {
                    toast("用户名不能为空")
                }
                is RegisterViewEvent.PasswordIsNotEmpty -> {
                    toast("密码不能为空")
                }
                is RegisterViewEvent.ConfirmPasswordIsNotEmpty -> {
                    toast("确认密码不能为空")
                }
                is RegisterViewEvent.RegisterSuccess -> {
                    toast("注册成功")
                    onRegisterSuccess()
                }
                is RegisterViewEvent.RegisterError -> {
                    toast("注册失败：${it.message}")
                }
                else -> {

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
            text = stringResource(R.string.register_tip),
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
            value = uiState.username,
            onValueChange = {
                viewModel.dispatch(RegisterViewAction.InputUsernameAction(it))
            },
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.username)) }
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(0.dp, 8.dp)
                .fillMaxWidth(),
            value = uiState.password,
            singleLine = true,
            onValueChange = {
                viewModel.dispatch(RegisterViewAction.InputPasswordAction(it))
            },
            label = { Text(text = stringResource(id = R.string.password)) },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(0.dp, 8.dp)
                .fillMaxWidth(),
            value = uiState.confirmPassword,
            singleLine = true,
            onValueChange = {
                viewModel.dispatch(RegisterViewAction.InputConfirmPasswordAction(it))
            },
            label = { Text(text = stringResource(id = R.string.enter_password_again)) },
            visualTransformation = PasswordVisualTransformation()
        )
        Text(
            text = stringResource(id = R.string.register),
            modifier = Modifier
                .padding(0.dp, 24.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(WanAndroidTheme.colors.colorAccent)
                .clickable { viewModel.dispatch(RegisterViewAction.RegisterAction) }
                .padding(12.dp),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.have_account),
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 24.dp)
                .align(Alignment.End)
                .clickable { onLoginClick() },
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen()
}