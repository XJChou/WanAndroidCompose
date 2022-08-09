package com.zxj.wanandroid.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.wanandroid.compose.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoginViewEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

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
        viewModelScope.launch {
            // step1: 检查当前账号和密码
            val username = uiState.value.username
            val password = uiState.value.password
            if (!assetParameters(username, password)) {
                return@launch
            }

            // step2: 网络访问
            viewModelScope.launch {
                userRepository
                    .signIn(username, password)
                    .onSuspendSuccess {
                        _uiEvent.send(LoginViewEvent.LoginSuccess)
                    }
                    .onSuspendError {
                        _uiEvent.send(LoginViewEvent.LoginError(it))
                    }
            }
        }
    }

    private suspend fun assetParameters(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            _uiEvent.send(LoginViewEvent.UserNameIsNotEmpty)
            return false
        }
        if (password.isEmpty()) {
            _uiEvent.send(LoginViewEvent.PasswordIsNotEmpty)
            return false
        }
        return true
    }
}

data class LoginUIState(
    val username: String = "",
    val password: String = ""
)

sealed class LoginViewAction {
    object LoginAction : LoginViewAction()
    class InputUsernameAction(val username: String) : LoginViewAction()
    class InputPasswordAction(val password: String) : LoginViewAction()
}

sealed class LoginViewEvent {
    object UserNameIsNotEmpty : LoginViewEvent()
    object PasswordIsNotEmpty : LoginViewEvent()
    object LoginSuccess : LoginViewEvent()
    class LoginError(val message: String) : LoginViewEvent()
}