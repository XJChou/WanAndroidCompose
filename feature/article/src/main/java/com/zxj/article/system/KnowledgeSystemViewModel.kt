package com.zxj.article.system

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSONObject
import com.zxj.article.navigation.knowledgeSystemDetailKnowledgesArgs
import com.zxj.article.navigation.knowledgeSystemDetailNameArgs
import com.zxj.data.repository.ArticleRepository
import com.zxj.model.Knowledge
import com.zxj.ui.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KnowledgeSystemViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<Status>(Status.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { Status.Loading }
            val response = articleRepository.loadKnowledgeTree()
            if (response.isSuccess) {
                _uiState.update { Status.Success(response.data) }
            } else {
                _uiState.update { Status.Error(response.errorMsgOrDefault) }
            }
        }
    }
}