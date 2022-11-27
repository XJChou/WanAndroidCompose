package com.zxj.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.data.repository.UserRepository
import com.zxj.model.USER_EMPTY
import com.zxj.model.UserBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    init {
        loadUserInfo()
    }

    val userFlow: StateFlow<UserBean> = userRepository.user
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000),
            initialValue = USER_EMPTY
        )

    fun signOut() {
        viewModelScope.launch { userRepository.signOut() }
    }

    fun loadUserInfo() {
        viewModelScope.launch { userRepository.loadUserInfo() }
    }

}
