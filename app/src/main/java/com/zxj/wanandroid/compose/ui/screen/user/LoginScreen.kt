package com.zxj.wanandroid.compose.ui.screen.user

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zxj.wanandroid.compose.NavigationRoute
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.GetString
import com.zxj.wanandroid.compose.application.toast
import com.zxj.wanandroid.compose.ui.ControlBean
import com.zxj.wanandroid.compose.ui.Toolbar
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.LoginViewAction
import com.zxj.wanandroid.compose.viewmodel.LoginViewEvent
import com.zxj.wanandroid.compose.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(navController: NavHostController) {
    Column(Modifier.fillMaxSize()) {
        val leftControls = remember(navController) {
            arrayListOf(ControlBean(R.drawable.ic_back) {
                navController.popBackStack()
            })
        }
        Toolbar(leftControl = leftControls) {
            Text(
                text = GetString(R.string.login),
                fontSize = 18.sp,
                color = WanAndroidTheme.colors.itemTagTv
            )
        }
        // 内容
        LoginPage(
            onRegisterClick = {
                navController.popBackStack()
                navController.navigate(NavigationRoute.REGISTER)
            },
            onLoginSuccess = {
                navController.popBackStack()
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
            text = GetString(R.string.user_login),
            color = WanAndroidTheme.colors.commonColor,
            fontSize = 18.sp
        )
        Text(
            text = GetString(R.string.register_tip),
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
            label = { Text(text = GetString(id = R.string.username)) }
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
            label = { Text(text = GetString(id = R.string.password)) },
            visualTransformation = PasswordVisualTransformation()
        )
        Text(
            text = GetString(id = R.string.login),
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
            text = GetString(id = R.string.no_account),
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
        LoginScreen(rememberNavController())
    }
}