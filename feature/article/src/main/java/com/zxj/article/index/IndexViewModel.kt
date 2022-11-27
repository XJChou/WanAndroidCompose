package com.zxj.article.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.zxj.data.createIntPagingFlow
import com.zxj.model.API
import com.zxj.model.ArticleBean
import com.zxj.model.ListData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor(
    private val indexRepository: com.zxj.data.repository.ArticleRepository,
    private val articleRepository: com.zxj.data.repository.ArticleRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(IndexViewState())
    val uiState = _uiState.asStateFlow()

    val pageItems = viewModelScope.createIntPagingFlow(::fetch)

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    /* ------------------------ action处理 ------------------------ */
    private suspend fun fetch(pageIndex: Int): API<ListData<ArticleBean>> {
        return if (pageIndex == 1) {
            coroutineScope {
                val bannerListAwait = async { indexRepository.loadBannerList() }
                val dataListAwait = async { indexRepository.loadDataList(pageIndex) }
                awaitAll(bannerListAwait, dataListAwait)
                val bannerListResponse = bannerListAwait.await().also { response ->
                    _uiState.update { it.copy(bannerList = response.data ?: emptyList()) }
                }
                val dataListResponse = dataListAwait.await()
                API(
                    errorCode = bannerListResponse.errorCode + dataListResponse.errorCode,
                    errorMsg = dataListResponse.errorMsg ?: bannerListResponse.errorMsg,
                    dataListResponse.data
                )
            }
        } else {
            indexRepository.loadDataList(pageIndex)
        }
    }

    /**
     * 处理点赞action
     */
    fun dealZanAction(collect: Boolean, data: ArticleBean) {
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


data class IndexViewState(
    val bannerList: List<com.zxj.model.BannerBean> = emptyList(),
)

interface UIEvent {
    data class ShowToast(val msg: String) : UIEvent
}