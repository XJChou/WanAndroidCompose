package com.zxj.wanandroid.compose.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.wanandroid.compose.data.bean.HistorySearchBean
import com.zxj.wanandroid.compose.data.bean.HotSearchBean
import com.zxj.wanandroid.compose.data.repositories.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    /**
     * 加载2部分：
     *
     * 1. 加载热门搜索内容
     * 2. 加载搜索历史内容
     */
    val uiState: StateFlow<SearchViewState> = combine(
        searchRepository.loadHistorySearchList(),
        searchRepository.loadHotSearchList(),
        transform = { historySearchList, hotSearchList ->
            SearchViewState(hotSearchList, historySearchList)
        }
    ).stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(1000),
        initialValue = SearchViewState()
    )

    init {
        dispatch(SearchViewAction.InitAction)
    }

    fun dispatch(action: SearchViewAction) {
        when (action) {
            is SearchViewAction.InitAction -> {
                refreshHotSearchList()
            }
            is SearchViewAction.SearchHistoryAction -> {
                search(action.search)
            }
            is SearchViewAction.DeleteHistoryAction -> {
                deleteHistorySearch(action.item)
            }
            is SearchViewAction.ClearHistoryAction -> {
                clearHistorySearch()
            }
        }
    }

    private fun deleteHistorySearch(item: HistorySearchBean) {
        viewModelScope.launch {
            searchRepository.deleteHistorySearch(item)
        }
    }

    private fun search(search: String) {
        viewModelScope.launch {
            searchRepository.insertHistorySearch(search)
        }
    }

    private fun refreshHotSearchList() {
        viewModelScope.launch {
            searchRepository.refreshHotSearchList()
        }
    }

    private fun clearHistorySearch() {
        viewModelScope.launch {
            searchRepository.clearHistorySearch()
        }
    }
}

data class SearchViewState(
    val hotSearchList: List<HotSearchBean> = listOf(),
    val searchHistoryList: List<HistorySearchBean> = listOf()
)

sealed class SearchViewEvent {

}

sealed class SearchViewAction {
    object InitAction : SearchViewAction()

    class SearchHistoryAction(val search: String) : SearchViewAction()
    class DeleteHistoryAction(val item: HistorySearchBean) : SearchViewAction()
    object ClearHistoryAction : SearchViewAction()
}