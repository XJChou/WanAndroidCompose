package com.zxj.wanandroid.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.getString
import com.zxj.wanandroid.compose.data.repositories.UserRepository
import com.zxj.wanandroid.compose.ui.NavigationItemBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val drawerUIState: StateFlow<DrawerUIState> = userRepository.user.map {
        DrawerUIState(it.token.isNotEmpty())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000),
        initialValue = DrawerUIState()
    )

    val navigationItems = arrayOf(
        NavigationItemBean(R.drawable.ic_home_black_24dp, R.string.navigation_text_home),
        NavigationItemBean(R.drawable.ic_square_black_24dp, R.string.navigation_text_square),
        NavigationItemBean(R.drawable.ic_wechat_black_24dp, R.string.navigation_text_public),
        NavigationItemBean(R.drawable.ic_apps_black_24dp, R.string.navigation_text_system),
        NavigationItemBean(R.drawable.ic_project_black_24dp, R.string.navigation_text_project),
    )

    val TITLE = arrayOf(
        getString(R.string.toolbar_text_home),
        getString(R.string.toolbar_text_square),
        getString(R.string.toolbar_text_public),
        getString(R.string.toolbar_text_system),
        getString(R.string.toolbar_text_project)
    )


}

data class DrawerUIState(
    val isLogin: Boolean = false
)