package com.zxj.wanandroid.compose.viewmodel

import androidx.lifecycle.ViewModel
import com.zxj.wanandroid.compose.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

}