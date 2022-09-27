package com.zxj.wanandroid.compose.ui.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.zxj.wanandroid.compose.data.bean.ListData
import com.zxj.wanandroid.compose.data.bean.UserScoreBean
import com.zxj.wanandroid.compose.data.repositories.UserRepository
import com.zxj.wanandroid.compose.net.API
import com.zxj.wanandroid.compose.utils.createIntPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val scoreFlow = userRepository.userInfo.map { it?.coinCount }.distinctUntilChanged()

    private val pager = Pager(PagingConfig(20)) { createIntPagingSource(::fetch) }
    val pagingItems = pager.flow.cachedIn(viewModelScope)

    private suspend fun fetch(pageIndex: Int): API<ListData<UserScoreBean>> {
        return userRepository.loadUserScoreList(pageIndex)
    }

}