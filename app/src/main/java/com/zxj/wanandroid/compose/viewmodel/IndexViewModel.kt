package com.zxj.wanandroid.compose.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.wanandroid.compose.data.bean.BannerBean
import com.zxj.wanandroid.compose.data.bean.Data
import com.zxj.wanandroid.compose.data.repositories.IndexRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor(
    private val indexRepository: IndexRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(IndexViewState())
    val uiState = _uiState.asStateFlow()

    private var pageIndex = 1

    init {
        dispatch(IndexViewAction.RefreshAction)
    }

    /* ------------------------ action处理 ------------------------ */
    fun dispatch(action: IndexViewAction) = when (action) {
        IndexViewAction.RefreshAction -> {
            dispatchRefresh()
        }
        IndexViewAction.LoadAction -> {
            dispatchLoadAction()
        }
    }

    private fun dispatchRefresh() {
        if (_uiState.value.isRefresh || _uiState.value.isLoad) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(refreshFetchStatus = FetchStatus.Fetching)
            val bannerListAwait = async { indexRepository.loadBannerList() }
            val articleListAwait = async { indexRepository.loadDataList(1) }

            val articleListResponse = articleListAwait.await()
            val bannerListResponse = bannerListAwait.await()
            if (articleListResponse.isSuccess && bannerListResponse.isSuccess) {
                _uiState.value = _uiState.value.copy(
                    refreshFetchStatus = FetchStatus.Success,
                    bannerList = bannerListResponse.data,
                    articleList = articleListResponse.data?.datas,
                    hasLoad = true
                )
                pageIndex = 1
            } else {
                _uiState.value = _uiState.value.copy(refreshFetchStatus = FetchStatus.Error)
            }
        }
    }

    private fun dispatchLoadAction() {
        if (_uiState.value.isRefresh || _uiState.value.isLoad) return

        val nextPage = this.pageIndex + 1
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loadFetchStatus = FetchStatus.Fetching)
            indexRepository.loadDataList(nextPage)
                .onSuccess {
                    val targetList = _uiState.value.articleList as MutableList
                    val networkData = it?.datas
                    if (!networkData.isNullOrEmpty()) {
                        targetList.addAll(targetList)
                    }

                    _uiState.value = _uiState.value.copy(
                        loadFetchStatus = FetchStatus.Success,
                        articleList = targetList,
                        hasLoad = !it?.datas.isNullOrEmpty()
                    )
                    this@IndexViewModel.pageIndex = nextPage
                }
                .onError {
                    _uiState.value = _uiState.value.copy(loadFetchStatus = FetchStatus.Error)
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
    val refreshFetchStatus: FetchStatus = FetchStatus.NotFetched,
    val bannerList: List<BannerBean>? = null,
    val articleList: List<Data>? = null,
    val loadFetchStatus: FetchStatus = FetchStatus.NotFetched,
    val hasLoad: Boolean = false
) {
    val isRefresh: Boolean
        get() = refreshFetchStatus == FetchStatus.Fetching

    val isLoad: Boolean
        get() = loadFetchStatus == FetchStatus.Fetching
}

sealed class FetchStatus {
    object NotFetched : FetchStatus()
    object Fetching : FetchStatus()
    object Success : FetchStatus()
    object Error : FetchStatus()
}

/**
 * 页面一次性事件
 */
sealed class IndexViewEvent {
    /**
     * 显示toast
     */
    data class ShowToast(@StringRes val messageId: Int) : IndexViewEvent()
}


/**
 * 用户交互操作 action [ user -> model ]
 */
sealed class IndexViewAction {

    /**
     * 刷新的时候
     */
    object RefreshAction : IndexViewAction()

    /**
     * 加载的时候
     */
    object LoadAction : IndexViewAction()
}