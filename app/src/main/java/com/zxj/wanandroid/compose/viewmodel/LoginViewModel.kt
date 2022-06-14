package com.zxj.wanandroid.compose.viewmodel

import androidx.lifecycle.ViewModel
import com.zxj.wanandroid.compose.data.repositories.IndexRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginViewState())
    var uiState = _uiState.asStateFlow()

    fun dispatch(action: LoginViewAction) {
        when (action) {
            LoginViewAction.LoginAction -> {
                dispatchLogin()
            }
            is LoginViewAction.InputUsernameAction -> {
                _uiState.value = _uiState.value.copy(username = action.username)
            }
            is LoginViewAction.InputPasswordAction -> {
                _uiState.value = _uiState.value.copy(password = action.password)
            }
        }
    }

    private fun dispatchLogin() {
        // step1: 检查当前账号和密码

        // step2: 网络访问

        // step3: 输出结果
    }
}

data class LoginViewState(
    val isLogin: Boolean = false,
    val username: String = "",
    val password: String = ""
)

sealed class LoginViewAction {

    object LoginAction : LoginViewAction()

    class InputUsernameAction(val username: String) : LoginViewAction()

    class InputPasswordAction(val password: String) : LoginViewAction()
}

sealed class LoginViewEvent {

}