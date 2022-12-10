package com.zxj.article.system

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.data.repository.ArticleRepository
import com.zxj.ui.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Status>(Status.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { Status.Loading }
            articleRepository.loadNavigation().also { response ->
                if (response.isSuccess) {
                    _uiState.update { Status.Success(response.data) }
                } else {
                    _uiState.update { Status.Error(response.errorMsgOrDefault) }
                }
            }
        }
    }

}