package com.zxj.wanandroid.compose.ui.collect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.wanandroid.compose.data.bean.CollectionArticle
import com.zxj.wanandroid.compose.data.repositories.ArticleRepository
import com.zxj.wanandroid.compose.widget.NextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CollectUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<CollectUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var page = 0
    private var job: Job? = null

    init {
        refresh()
    }

    fun refresh() {
        if (job?.isActive == true) return

        _uiState.update { it.copy(refresh = true) }

        job = viewModelScope.launch {
            articleRepository.loadCollectArticleList(0)
                .ifSuccess { response ->
                    page = 0
                    _uiState.update {
                        it.copy(
                            refresh = false,
                            dataList = response?.datas ?: emptyList(),
                            nextState = if (response?.over != false) {
                                if (response?.datas.isNullOrEmpty()) {
                                    NextState.STATE_NONE
                                } else {
                                    NextState.STATE_FINISH_OVER
                                }
                            } else {
                                NextState.STATE_FINISH_PART
                            }
                        )
                    }
                }
                .ifError {
                    _uiState.update { it.copy(refresh = false) }
                    _uiEvent.trySend(CollectUIEvent.ShowToast(it))
                }
        }
    }

    fun nextPage() {
        if (job?.isActive == true) return

        val nextPage = page + 1
        job = viewModelScope.launch {
            _uiState.update { it.copy(nextState = NextState.STATE_LOADING) }
            articleRepository.loadCollectArticleList(nextPage)
                .ifSuccess { response ->
                    page = nextPage
                    _uiState.update {
                        val nextDataList = ArrayList(it.dataList!!).also {
                            it.addAll(response?.datas ?: emptyList())
                        }
                        it.copy(
                            dataList = nextDataList,
                            nextState = if (response?.over != false) {
                                NextState.STATE_FINISH_OVER
                            } else {
                                NextState.STATE_FINISH_PART
                            }
                        )
                    }
                }
                .ifError {
                    _uiState.update {
                        it.copy(nextState = NextState.STATE_ERROR)
                    }
                }
        }
    }

    fun removeCollect(collectionArticle: CollectionArticle) {
        if (job?.isActive == true) return
        job = viewModelScope.launch {
            articleRepository.removeCollectArticle(collectionArticle.originId)
                .ifSuccess {
                    _uiState.update {
                        it.copy(dataList = it.dataList?.filter { it.originId != collectionArticle.originId })
                    }
                }
                .ifError {
                    _uiEvent.trySend(CollectUIEvent.ShowToast(it))
                }
        }

    }
}

/**
 * 收藏Screen的状态
 * @param boxState Box的状态
 * @param refresh 当前是否刷新
 * @param dataList 数据列表
 * @param nextState 当前加载状态
 */
data class CollectUiState(
    val refresh: Boolean = false,
    val dataList: List<CollectionArticle>? = null,
    val nextState: Int = NextState.STATE_NONE,
)

sealed interface CollectUIEvent {
    data class ShowToast(val msg: String) : CollectUIEvent
}