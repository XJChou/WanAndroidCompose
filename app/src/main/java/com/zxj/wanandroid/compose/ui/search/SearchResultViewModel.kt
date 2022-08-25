package com.zxj.wanandroid.compose.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.wanandroid.compose.data.bean.Data
import com.zxj.wanandroid.compose.data.repositories.ArticleRepository
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

    private val _uiState = MutableStateFlow(SearchResultUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var job: Job? = null
    val searchContext: String = checkNotNull(savedStateHandle["content"])

    init {
        refresh()
        collectZan()
    }

    private fun collectZan() {
        viewModelScope.launch {
            articleRepository.collectFlow.collect { pair ->
                val value = _uiState.value
                var changed = false
                val newData = value.data.map {
                    if (it.id == pair.first) {
                        changed = true
                        it.copy(collect = pair.second)
                    } else {
                        it
                    }
                }
                if (changed) {
                    _uiState.compareAndSet(value, value.copy(data = newData))
                }
            }
        }
    }

    /**
     * 刷新
     */
    fun refresh() {
        if (job?.isActive == true) return
        job = viewModelScope.launch {
            _uiState.update { it.copy(refresh = true) }
            articleRepository.loadSearchArticleList(0, searchContext)
                .ifSuccess {
                    _uiState.update {
                        it.copy(
                            refresh = false,
                            page = 1,
                            data = this.data?.datas ?: emptyList(),
                            nextState = if (this.data?.over != false) NextState.STATE_FINISH_OVER else NextState.STATE_FINISH_PART
                        )
                    }
                }
                .ifError {
                    _uiState.update { it.copy(refresh = false) }
                }
        }
    }

    /**
     * 下一页
     */
    fun nextPage() {
        if (job?.isActive == true) return
        job = viewModelScope.launch {
            _uiState.update {
                it.copy(nextState = NextState.STATE_LOADING)
            }
            val nextPage = _uiState.value.page + 1
            articleRepository.loadSearchArticleList(nextPage - 1, searchContext)
                .ifSuccess {
                    _uiState.update {
                        it.copy(
                            nextState = if (this.data?.over != false) NextState.STATE_FINISH_OVER else NextState.STATE_FINISH_PART,
                            page = nextPage,
                            data = ArrayList(it.data).also {
                                it.addAll(this.data?.datas ?: emptyList())
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

    /**
     * 处理点赞action
     */
    fun dealZanAction(collect: Boolean, data: Data) {
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


data class SearchResultUiState(
    val data: List<Data> = emptyList(),
    val page: Int = 1,
    val refresh: Boolean = false,
    val nextState: Int = NextState.STATE_NONE
)

interface UIEvent {
    data class ShowToast(val msg: String) : UIEvent
}