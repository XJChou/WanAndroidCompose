package com.zxj.user

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.ui.TextToolBar
import com.zxj.ui.ToolBarIcon
import com.zxj.wanandroid.compose.viewmodel.RegisterViewAction
import com.zxj.wanandroid.compose.viewmodel.RegisterViewEvent
import com.zxj.wanandroid.compose.viewmodel.RegisterViewModel
import kotlinx.coroutines.flow.collectLatest
import com.zxj.ui.R.drawable as UIDrawable

@Composable
internal fun RegisterRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    navigateToLogin: () -> Unit
) {
    RegisterScreen(
        modifier = modifier,
        onBack = onBack,
        loginAction = navigateToLogin
    )
}


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    loginAction: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        TextToolBar(
            title = stringResource(R.string.register),
            fitsSystemWindows = true,
            navigationIcon = {
                ToolBarIcon(drawableRes = UIDrawable.ic_back, onBack)
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
    val context = LocalContext.current
    LaunchedEffect(key1 = viewModel) {
        viewModel.uiEvent.collectLatest {
            when (it) {
                is RegisterViewEvent.UserNameIsNotEmpty -> {
                    context.toast("用户名不能为空")
                }
                is RegisterViewEvent.PasswordIsNotEmpty -> {
                    context.toast("密码不能为空")
                }
                is RegisterViewEvent.ConfirmPasswordIsNotEmpty -> {
                    context.toast("确认密码不能为空")
                }
                is RegisterViewEvent.RegisterSuccess -> {
                    context.toast("注册成功")
                    onRegisterSuccess()
                }
                is RegisterViewEvent.RegisterError -> {
                    context.toast("注册失败：${it.message}")
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
    RegisterScreen(
        modifier = Modifier.fillMaxSize(),
        onBack = {},
        loginAction = {}
    )
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}