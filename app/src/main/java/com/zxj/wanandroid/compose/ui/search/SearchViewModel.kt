package com.zxj.wanandroid.compose.ui.search

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.wanandroid.compose.R
import com.zxj.wanandroid.compose.application.getString
import com.zxj.wanandroid.compose.data.bean.HistorySearchBean
import com.zxj.wanandroid.compose.data.bean.HotSearchBean
import com.zxj.wanandroid.compose.data.repositories.SearchRepository
import com.zxj.wanandroid.compose.ui.theme.WanAndroidTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
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
                _uiEvent.send(SearchViewEvent.ShowToast(getString(R.string.search_not_empty)))
                return@launch
            }
            _uiEvent.send(SearchViewEvent.SearchSuccess(search))
            searchRepository.insertHistorySearch(search)
        }
    }

    private fun refreshHotSearchList() {
        viewModelScope.launch {
            searchRepository.refreshHotSearchList().ifSuspendError {
                _uiEvent.send(SearchViewEvent.ShowToast(getString(R.string.network_unavailable_tip)))
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

private fun randomColor(): Int {
    val random = Random()
    //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
    var red = random.nextInt(190)
    var green = random.nextInt(190)
    var blue = random.nextInt(190)
    if (WanAndroidTheme.theme == WanAndroidTheme.Theme.Night) {
        //150-255
        red = random.nextInt(105) + 150
        green = random.nextInt(105) + 150
        blue = random.nextInt(105) + 150
    }
    //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
    return android.graphics.Color.rgb(red, green, blue)
}
