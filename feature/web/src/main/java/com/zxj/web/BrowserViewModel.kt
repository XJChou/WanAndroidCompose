package com.zxj.web

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.zxj.web.navigation.BrowserArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BrowserViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val browserArgs = BrowserArgs(savedStateHandle)

    val url = browserArgs.url


}