package com.zxj.rank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.zxj.data.createIntPagingSource
import com.zxj.data.repository.UserRepository
import com.zxj.model.API
import com.zxj.model.ListData
import com.zxj.model.RankBean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RankViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var pager = Pager(PagingConfig(20)) { createIntPagingSource(::fetch) }
    val pagingData = pager.flow.cachedIn(viewModelScope)

    private suspend fun fetch(pageIndex: Int): API<ListData<RankBean>> {
        return userRepository.loadRankList(pageIndex - 1)
    }
}
