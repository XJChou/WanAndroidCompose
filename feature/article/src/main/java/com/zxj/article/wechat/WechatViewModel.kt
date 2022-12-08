package com.zxj.article.wechat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.data.repository.ArticleRepository
import com.zxj.model.WechatChapterBean
import com.zxj.ui.Status
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
        _uiState.value = Status.Loading
        viewModelScope.launch {
            val response = articleRepository.loadWechatChapters()
            if (response.isSuccess) {
                _uiState.value = Status.Success(response.data)
            } else {
                _uiState.value = Status.Error(response.errorMsgOrDefault)
            }
        }
    }
}

