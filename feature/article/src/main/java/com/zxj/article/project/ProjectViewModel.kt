package com.zxj.article.project

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zxj.data.createIntPagingSource
import com.zxj.data.repository.ArticleRepository
import com.zxj.model.ArticleBean
import com.zxj.ui.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<Status>(Status.Initial)
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<String>()
    val event = _event.asSharedFlow()

    val collectEvent = articleRepository.collectFlow

    private val pagerMap = mutableStateMapOf<Int, Flow<PagingData<ArticleBean>>>()
    private val collectMap = mutableStateMapOf<String, Boolean>()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { Status.Loading }
            val response = articleRepository.loadProjectTree()
            if (response.isSuccess) {
                _uiState.update { Status.Success(response.data) }
            } else {
                _uiState.update { Status.Error(response.errorMsgOrDefault) }
            }
        }
        viewModelScope.launch {
            collectEvent.collect { event ->
                collectMap[event.first] = event.second
            }
        }
    }

    private suspend fun fetchData(page: Int, cid: Int) =
        articleRepository.loadProjectList(page, cid)

    fun getPagingFlow(cid: Int): Flow<PagingData<ArticleBean>> {
        if (!pagerMap.containsKey(cid)) {
            pagerMap[cid] = Pager(PagingConfig(20)) { createIntPagingSource { fetchData(it, cid) } }
                .flow
                .cachedIn(viewModelScope)
        }
        return pagerMap[cid]!!
    }

    fun getCollect(id: String) = collectMap[id]

    fun collectItem(articleBean: ArticleBean, collect: Boolean) {
        viewModelScope.launch {
            val response = if (collect) {
                articleRepository.addCollectArticle(articleBean.id)
            } else {
                articleRepository.removeCollectArticle(articleBean.id)
            }
            if (!response.isSuccess) {
                _event.emit(response.errorMsgOrDefault)
            }
        }
    }
}

