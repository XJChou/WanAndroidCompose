package com.zxj.wanandroid.compose.ui.collect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.zxj.wanandroid.compose.data.bean.CollectionArticle
import com.zxj.wanandroid.compose.data.bean.ListData
import com.zxj.wanandroid.compose.data.repositories.ArticleRepository
import com.zxj.wanandroid.compose.net.API
import com.zxj.wanandroid.compose.utils.createIntPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _removeItemFlow = MutableStateFlow<List<Int>>(listOf())
    private val removedItemsFlow: Flow<List<Int>> get() = _removeItemFlow.asStateFlow()

    private var pager = Pager(PagingConfig(20)) { createIntPagingSource(::fetch) }
    val pageData = pager.flow.cachedIn(viewModelScope)
        .combine(removedItemsFlow) { pagingData, removed ->
            pagingData.filter { it.originId !in removed }
        }

    private val _uiEvent = Channel<CollectUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private suspend fun fetch(pageIndex: Int): API<ListData<CollectionArticle>> {
        return articleRepository.loadCollectArticleList(pageIndex - 1)
    }

    fun removeCollect(collectionArticle: CollectionArticle) {
        viewModelScope.launch {
            articleRepository.removeCollectArticle(collectionArticle.originId)
                .ifSuccess {
                    _removeItemFlow.value = _removeItemFlow.value + collectionArticle.originId
                }
                .ifError {
                    _uiEvent.trySend(CollectUIEvent.ShowToast(it))
                }
        }
    }
}

sealed interface CollectUIEvent {
    data class ShowToast(val msg: String) : CollectUIEvent
}