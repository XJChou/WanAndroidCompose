package com.zxj.search

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.data.repository.SearchRepository
import com.zxj.designsystem.theme.WanAndroidTheme
import com.zxj.model.HistorySearchBean
import com.zxj.model.HotSearchBean
import com.zxj.ui.randomColor
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val searchRepository: SearchRepository
) : ViewModel() {

    // 相关数据流
    private val searchListFlow = searchRepository.loadHistorySearchList()
    private val hotSearchListFlow = searchRepository.loadHotSearchList()
    private val hotSearchColorListFlow = hotSearchListFlow.map { it.map { Color(randomColor()) } }

    /**
     * 加载2部分：
     *
     * 1. 加载热门搜索内容
     * 2. 加载搜索历史内容
     */
    val uiState: StateFlow<SearchViewState> = combine(
        searchListFlow,
        hotSearchListFlow,
        hotSearchColorListFlow,
        transform = { historySearchList, hotSearchList, hotSearchColorList ->
            SearchViewState(
                hotSearchList,
                hotSearchColorList,
                historySearchList
            )
        }
    ).stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(1000),
        initialValue = SearchViewState()
    )

    private val _uiEvent = Channel<SearchViewEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        refreshHotSearchList()
    }

    fun deleteHistorySearch(item: HistorySearchBean) {
        viewModelScope.launch {
            searchRepository.deleteHistorySearch(item)
        }
    }

    fun search(search: String) {
        viewModelScope.launch {
            if (search.isEmpty()) {
                _uiEvent.send(SearchViewEvent.ShowToast(context.getString(R.string.search_not_empty)))
                return@launch
            }
            _uiEvent.send(SearchViewEvent.SearchSuccess(search))
            searchRepository.insertHistorySearch(search)
        }
    }

    private fun refreshHotSearchList() {
        viewModelScope.launch {
            searchRepository.refreshHotSearchList().ifSuspendError {
                _uiEvent.send(SearchViewEvent.ShowToast(context.getString(com.zxj.data.R.string.network_unavailable_tip)))
            }
        }
    }

    fun clearHistorySearch() {
        viewModelScope.launch {
            searchRepository.clearHistorySearch()
        }
    }
}

data class SearchViewState(
    val hotSearchList: List<HotSearchBean> = listOf(),
    val hotSearchColorList: List<Color> = listOf(),
    val searchHistoryList: List<HistorySearchBean> = listOf()
)

sealed class SearchViewEvent {
    class ShowToast(val msg: String) : SearchViewEvent()
    class SearchSuccess(val search: String) : SearchViewEvent()
}

