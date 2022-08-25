package com.zxj.wanandroid.compose.ui.collect

import androidx.lifecycle.ViewModel
import com.zxj.wanandroid.compose.data.bean.CollectionArticle
import com.zxj.wanandroid.compose.widget.NextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CollectViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(CollectUiState())
    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _uiState.update { it.copy(refresh = true) }

    }

    fun nextPage() {
        TODO("Not yet implemented")
    }
}

data class CollectUiState(
    val refresh: Boolean = false,
    val dataList: List<CollectionArticle> = emptyList(),
    val nextState: Int = NextState.STATE_NONE,
)