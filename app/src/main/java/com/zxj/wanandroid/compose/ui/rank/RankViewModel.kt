package com.zxj.wanandroid.compose.ui.rank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxj.wanandroid.compose.data.bean.CoinInfoBean
import com.zxj.wanandroid.compose.data.bean.CollectionArticle
import com.zxj.wanandroid.compose.data.bean.UserScoreBean
import com.zxj.wanandroid.compose.data.repositories.UserRepository
import com.zxj.wanandroid.compose.ui.collect.CollectUIEvent
import com.zxj.wanandroid.compose.ui.collect.CollectUiState
import com.zxj.wanandroid.compose.widget.NextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RankUIState())
    val uiState = _uiState.asStateFlow()

    private var page = 0
    private var job: Job? = null

    init {
        refresh()
    }

    fun refresh() {
        if (job?.isActive == true) return

        _uiState.update { it.copy(refresh = true) }

        job = viewModelScope.launch {
            userRepository.loadRankList(0)
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
                }
        }
    }

    fun nextPage() {
        if (job?.isActive == true) return

        val nextPage = page + 1
        job = viewModelScope.launch {
            _uiState.update { it.copy(nextState = NextState.STATE_LOADING) }
            userRepository.loadRankList(nextPage)
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
}

data class RankUIState(
    val refresh: Boolean = false,
    val dataList: List<CoinInfoBean>? = null,
    val nextState: Int = NextState.STATE_NONE,
)