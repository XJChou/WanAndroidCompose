package com.zxj.wanandroid.compose.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.zxj.wanandroid.compose.data.bean.Article
import com.zxj.wanandroid.compose.data.bean.ListData
import com.zxj.wanandroid.compose.data.repositories.ArticleRepository
import com.zxj.wanandroid.compose.net.API
import com.zxj.wanandroid.compose.utils.createIntPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val pager = Pager(PagingConfig(20)) { createIntPagingSource(::fetch) }
    val pagingItems = pager.flow.cachedIn(viewModelScope)

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val searchContext: String = checkNotNull(savedStateHandle["content"])

    private suspend fun fetch(pageIndex: Int): API<ListData<Article>> {
        return articleRepository.loadSearchArticleList(pageIndex - 1, searchContext)
    }

    /**
     * 处理点赞action
     */
    fun dealZanAction(collect: Boolean, data: Article) {
        viewModelScope.launch {
            val apiResponse = if (collect) {
                articleRepository.addCollectArticle(data.id)
            } else {
                articleRepository.removeCollectArticle(data.id)
            }
            apiResponse.ifSuspendSuccess { data.collect = collect }
                .ifSuspendError { _uiEvent.send(UIEvent.ShowToast(it)) }
        }
    }
}


interface UIEvent {
    data class ShowToast(val msg: String) : UIEvent
}