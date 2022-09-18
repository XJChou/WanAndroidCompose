package com.zxj.wanandroid.compose.ui.share

import androidx.lifecycle.ViewModel
import com.zxj.wanandroid.compose.data.repositories.RealArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val articleRepository: RealArticleRepository
) : ViewModel() {
}