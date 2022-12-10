package com.zxj.collect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.zxj.data.createIntPagingSource
import com.zxj.data.repository.ArticleRepository
import com.zxj.model.API
import com.zxj.model.CollectionBean
import com.zxj.model.ListData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _removeItemFlow = MutableStateFlow<List<String>>(listOf())
    private val removedItemsFlow: Flow<List<String>> get() = _removeItemFlow.asStateFlow()

    private var pager = Pager(PagingConfig(20)) { createIntPagingSource(::fetch) }
    val pagingData = pager.flow.cachedIn(viewModelScope)
        .combine(removedItemsFlow) { pagingData, removed ->
            pagingData.filter { it.originId !in removed }
        }

    private val _uiEvent = Channel<CollectUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private suspend fun fetch(pageIndex: Int): API<ListData<CollectionBean>> {
        return articleRepository.loadCollectArticleList(pageIndex - 1)
    }

    fun removeCollect(collectionArticle: CollectionBean) {
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