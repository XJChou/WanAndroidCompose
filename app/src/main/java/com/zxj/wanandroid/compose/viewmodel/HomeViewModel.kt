package com.zxj.wanandroid.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.getString
import com.zxj.wanandroid.compose.data.bean.User
import com.zxj.wanandroid.compose.data.repositories.UserRepository
import com.zxj.wanandroid.compose.widget.NavigationItemBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val drawerUIState: StateFlow<DrawerUIState> = combine(
        userRepository.isLogin.distinctUntilChanged().onEach { if (it) userRepository.loadUserInfo() },
        userRepository.user,
        transform = { isLogin, user ->
            DrawerUIState(isLogin, user)
        }
    )

        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000),
            initialValue = DrawerUIState()
        )


    fun signOut() {
        viewModelScope.launch { userRepository.signOut() }
    }

    fun loadUserInfo() {
        viewModelScope.launch { userRepository.loadUserInfo() }
    }

}

data class DrawerUIState(
    val isLogin: Boolean = false,
    val user: User = User()
)

