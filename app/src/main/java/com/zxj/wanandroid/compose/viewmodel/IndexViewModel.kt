package com.zxj.wanandroid.compose.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.zxj.wanandroid.compose.data.ArticleBean
import com.zxj.wanandroid.compose.data.BannerBean
import com.zxj.wanandroid.compose.net.APIFactory
import com.zxj.wanandroid.compose.net.IndexAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// model
class IndexViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(IndexViewState(FetchStatus.NotFetched))
    val uiState: StateFlow<IndexViewState> = _uiState

    private val indexAPI: IndexAPI by lazy { APIFactory.get() }

    private var index = 0

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
//        viewModelScope.launch {
//            val articleListAwait = async { indexAPI.loadArticleList(0) }
//            val bannerListAwait = async { indexAPI.loadBanner() }
//        }
    }

    private fun dispatchLoadAction() {

    }
}

/**
 * 承载了页面所有状态 state [ model -> view ]
 * 1. 当前加载的状态
 * 2. banner列表
 * 3. 文章列表
 */
data class IndexViewState(
    val fetchStatus: FetchStatus,
    val bannerList: List<BannerBean>? = null,
    val articleList: List<ArticleBean>? = null
)

sealed class FetchStatus {
    object NotFetched : FetchStatus()
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
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