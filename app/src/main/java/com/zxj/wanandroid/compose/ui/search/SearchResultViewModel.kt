package com.zxj.wanandroid.compose.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.zxj.wanandroid.compose.data.bean.Article
import com.zxj.wanandroid.compose.data.bean.ListData
import com.zxj.wanandroid.compose.data.repositories.ArticleRepository
import com.zxj.wanandroid.compose.net.API
import com.zxj.wanandroid.compose.utils.createIntPagingSource
import com.zxj.wanandroid.compose.widget.NextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val pager = Pager(PagingConfig(20)) { createIntPagingSource(::fetch) }
    val pagingItems = pager.flow.cachedIn(viewModelScope)
//        .combine(articleRepository.collectFlow, ::combineFlow)

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val searchContext: String = checkNotNull(savedStateHandle["content"])

    private suspend fun fetch(pageIndex: Int): API<ListData<Article>> {
        return articleRepository.loadSearchArticleList(pageIndex - 1, searchContext)
    }

    private suspend fun combineFlow(
        pageData: PagingData<Article>,
        collect: Pair<Int, Boolean>
    ) = pageData.map {
        if (it.id == collect.first && it.collect != collect.second) {
            it.copy(collect = collect.second)
        } else {
            it
        }
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
            apiResponse.ifSuspendError {
                _uiEvent.send(UIEvent.ShowToast(it))
            }
        }
    }
}


interface UIEvent {
    data class ShowToast(val msg: String) : UIEvent
}