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
        _uiState.tryEmit(Status.Loading)
        viewModelScope.launch {
            articleRepository.loadWechatChapters().ifSuccess {
                _uiState.tryEmit(Status.Success(it ?: emptyList<WechatChapterBean>()))
            }.ifError {
                _uiState.tryEmit(Status.Error(it))
            }
        }
    }
}

