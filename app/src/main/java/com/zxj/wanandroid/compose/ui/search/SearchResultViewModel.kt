package com.zxj.wanandroid.compose.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.wanandroid.compose.data.bean.Data
import com.zxj.wanandroid.compose.data.repositories.ArticleRepository
import com.zxj.wanandroid.compose.widget.NextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchResultUiState())
    val uiState = _uiState.asStateFlow()

    private var job: Job? = null
    val searchContext: String = checkNotNull(savedStateHandle["content"])

    init {
        refresh()
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
                            nextState = if(this.data?.over != false) NextState.STATE_FINISH_OVER else NextState.STATE_FINISH_PART
                        )
                    }
                }
                .ifError {
                    _uiState.update {
                        it.copy(refresh = false)
                    }
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
                            nextState = if(this.data?.over != false) NextState.STATE_FINISH_OVER else NextState.STATE_FINISH_PART,
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
}


data class SearchResultUiState(
    val data: List<Data> = emptyList(),
    val page: Int = 1,
    val refresh: Boolean = false,
    val nextState: Int = NextState.STATE_NONE
)

