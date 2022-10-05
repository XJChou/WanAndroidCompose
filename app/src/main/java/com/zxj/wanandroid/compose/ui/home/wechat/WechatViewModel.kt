package com.zxj.wanandroid.compose.ui.home.wechat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.wanandroid.compose.data.bean.WXChapterBean
import com.zxj.wanandroid.compose.data.repositories.ArticleRepository
import com.zxj.wanandroid.compose.widget.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WechatViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Status>(Status.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        _uiState.tryEmit(Status.Loading)
        viewModelScope.launch {
            articleRepository.loadWechatChapters().ifSuccess {
                _uiState.tryEmit(Status.Success(it ?: emptyList<WXChapterBean>()))
            }.ifError {
                _uiState.tryEmit(Status.Error(it))
            }
        }
    }
}

