package com.zxj.wanandroid.compose.ui.screen.user

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.GetString
import com.zxj.wanandroid.compose.ui.ControlBean
import com.zxj.wanandroid.compose.ui.Toolbar
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import com.zxj.wanandroid.compose.viewmodel.HomeViewModel
import com.zxj.wanandroid.compose.viewmodel.IndexViewModel
import com.zxj.wanandroid.compose.viewmodel.LoginViewAction
import com.zxj.wanandroid.compose.viewmodel.LoginViewModel

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
            modifier = Modifier
                .background(WanAndroidTheme.colors.viewBackground)
                .verticalScroll(rememberScrollState())
                .padding(24.dp, 40.dp, 24.dp, 0.dp)
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Composable
fun RegisterPage(modifier: Modifier) {
}

@Composable
fun LoginPage(modifier: Modifier) {
    val viewModel: LoginViewModel = viewModel(LocalContext.current as ComponentActivity)
    val uiState by viewModel.uiState.collectAsState()
    val username by remember { derivedStateOf { uiState.username } }
    val password by remember { derivedStateOf { uiState.password } }
    Column(modifier) {
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
                .clickable {

                }
                .padding(12.dp),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = GetString(id = R.string.no_account),
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 24.dp)
                .align(Alignment.End)
                .clickable {

                },
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