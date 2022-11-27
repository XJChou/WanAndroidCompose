package com.zxj.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.zxj.data.createIntPagingSource
import com.zxj.data.repository.ArticleRepository
import com.zxj.model.API
import com.zxj.model.ArticleBean
import com.zxj.model.ListData
import com.zxj.search.navigation.SearchDetailArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val searchDetailArgs = SearchDetailArgs(savedStateHandle = savedStateHandle)
    val searchContext = searchDetailArgs.searchContent

    private val pager = Pager(PagingConfig(20)) { createIntPagingSource(::fetch) }
    val pagingItems = pager.flow.cachedIn(viewModelScope)
    val collectFlow = articleRepository.collectFlow

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private suspend fun fetch(pageIndex: Int): API<ListData<ArticleBean>> {
        return articleRepository.loadSearchArticleList(
            pageIndex - 1,
            searchDetailArgs.searchContent
        )
    }

    fun toggleCollect(collect: Boolean, data: ArticleBean) {
        viewModelScope.launch {
            val apiResponse = if (collect) {
                articleRepository.addCollectArticle(data.id)
            } else {
                articleRepository.removeCollectArticle(data.id)
            }
            if (!apiResponse.isSuccess) {
                _uiEvent.emit(UIEvent.ShowToast(apiResponse.errorMsgOrDefault))
            }
        }
    }
}

interface UIEvent {
    data class ShowToast(val msg: String) : UIEvent
}