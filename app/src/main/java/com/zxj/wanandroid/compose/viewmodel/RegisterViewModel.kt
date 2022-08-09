package com.zxj.wanandroid.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.wanandroid.compose.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    // UI状态
    private val _uiState = MutableStateFlow(RegisterUIState())
    var uiState = _uiState.asStateFlow()

    // 事件
    private val _uiEvent = Channel<RegisterViewEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun dispatch(action: RegisterViewAction) {
        when (action) {
            RegisterViewAction.RegisterAction -> {
                dispatchRegister()
            }
            is RegisterViewAction.InputUsernameAction -> {
                _uiState.value = _uiState.value.copy(username = action.username)
            }
            is RegisterViewAction.InputPasswordAction -> {
                _uiState.value = _uiState.value.copy(password = action.password)
            }
            is RegisterViewAction.InputConfirmPasswordAction -> {
                _uiState.value = _uiState.value.copy(confirmPassword = action.confirmPassword)
            }
        }
    }

    private fun dispatchRegister() {
        viewModelScope.launch {
            val username = uiState.value.username
            val password = uiState.value.password
            val confirmPassword = uiState.value.confirmPassword

            // step1: 参数检查
            if (!assetParameters(username, password, confirmPassword)) {
                return@launch
            }

            // step2: 网络请求
            userRepository
                .register(username, password, confirmPassword)
                .onSuspendSuccess {
                    _uiEvent.send(RegisterViewEvent.RegisterSuccess)
                }
                .onSuspendError {
                    _uiEvent.send(RegisterViewEvent.RegisterError(it))
                }
        }
    }

    private suspend fun assetParameters(
        username: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (username.isEmpty()) {
            _uiEvent.send(RegisterViewEvent.UserNameIsNotEmpty)
            return false
        }
        if (password.isEmpty()) {
            _uiEvent.send(RegisterViewEvent.PasswordIsNotEmpty)
            return false
        }
        if (confirmPassword.isEmpty()) {
            _uiEvent.send(RegisterViewEvent.ConfirmPasswordIsNotEmpty)
            return false
        }
        if (password != confirmPassword) {
            _uiEvent.send(RegisterViewEvent.PasswordNotEqualConfirmPassword)
            return false
        }
        return true
    }
}

/**
 * 注册页面的UI状态
 */
data class RegisterUIState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)

/**
 * 注册页面的用户事件
 */
sealed class RegisterViewAction {
    object RegisterAction : RegisterViewAction()
    class InputUsernameAction(val username: String) : RegisterViewAction()
    class InputPasswordAction(val password: String) : RegisterViewAction()
    class InputConfirmPasswordAction(val confirmPassword: String) : RegisterViewAction()
}

/**
 * 注册页面的Event
 */
sealed class RegisterViewEvent {
    object UserNameIsNotEmpty : RegisterViewEvent()
    object PasswordIsNotEmpty : RegisterViewEvent()
    object ConfirmPasswordIsNotEmpty : RegisterViewEvent()
    object PasswordNotEqualConfirmPassword : RegisterViewEvent()
    object RegisterSuccess : RegisterViewEvent()
    class RegisterError(val message: String) : RegisterViewEvent()
}