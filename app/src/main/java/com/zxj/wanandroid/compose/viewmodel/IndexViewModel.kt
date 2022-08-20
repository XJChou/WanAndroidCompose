package com.zxj.wanandroid.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.wanandroid.compose.data.bean.BannerBean
import com.zxj.wanandroid.compose.data.bean.Data
import com.zxj.wanandroid.compose.data.repositories.ArticleRepository
import com.zxj.wanandroid.compose.widget.NextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor(
    private val indexRepository: ArticleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(IndexViewState())
    val uiState = _uiState.asStateFlow()
    private var job: Job? = null
    private var pageIndex = 1

    init {
        refresh()
    }

    /* ------------------------ action处理 ------------------------ */
    fun refresh() {
        if (job?.isActive == true) return
        job = viewModelScope.launch {
            _uiState.update { it.copy(refresh = true) }
            val bannerListAwait = async { indexRepository.loadBannerList() }
            val articleListAwait = async { indexRepository.loadDataList(1) }
            if (awaitAll(bannerListAwait, articleListAwait).all { it.isSuccess }) {
                val bannerList = bannerListAwait.await().data ?: emptyList()
                val articleData = articleListAwait.await().data
                _uiState.update {
                    it.copy(
                        refresh = false,
                        bannerList = bannerList,
                        articleList = articleData?.datas ?: emptyList(),
                        nextState = if (articleData?.over == true) NextState.STATE_FINISH_OVER else NextState.STATE_FINISH_PART
                    )
                }
                pageIndex = 1
            } else {
                _uiState.update { it.copy(refresh = false) }
            }
        }
    }

    fun nextPage() {
        if (job?.isActive == true) return
        val nextPage = this.pageIndex + 1
        job = viewModelScope.launch {
            _uiState.update { it.copy(nextState = NextState.STATE_LOADING) }
            indexRepository.loadDataList(nextPage)
                .ifSuccess { data ->
                    val originArticleList = (_uiState.value.articleList as MutableList).also {
                        val networkData = data?.datas ?: emptyList()
                        if (networkData.isNotEmpty()) it.addAll(networkData)
                    }
                    _uiState.update {
                        it.copy(
                            nextState = if (data?.over == true) NextState.STATE_FINISH_OVER else NextState.STATE_FINISH_PART,
                            articleList = originArticleList
                        )
                    }
                    this@IndexViewModel.pageIndex = nextPage
                }
                .ifError {
                    _uiState.update { it.copy(nextState = NextState.STATE_ERROR) }
                }
        }
    }
}

/**
 * 承载了页面所有状态 state [ model -> view ]
 * 1. 当前刷新状态
 * 2. banner列表
 * 3. 文章列表
 * 4. 当前加载状态
 */
data class IndexViewState(
    val refresh: Boolean = false,
    val bannerList: List<BannerBean> = emptyList(),
    val articleList: List<Data> = emptyList(),
    val nextState: Int = NextState.STATE_NONE,
)
