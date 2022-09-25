package com.zxj.wanandroid.compose.ui.share

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.zxj.wanandroid.compose.data.bean.Article
import com.zxj.wanandroid.compose.data.bean.ListData
import com.zxj.wanandroid.compose.data.repositories.RealArticleRepository
import com.zxj.wanandroid.compose.net.API
import com.zxj.wanandroid.compose.utils.createIntPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val articleRepository: RealArticleRepository
) : ViewModel() {

    private val page = Pager(PagingConfig(20)) { createIntPagingSource(fetch = ::fetch) }

    val pagingItems = page.flow.cachedIn(viewModelScope)

    private suspend fun fetch(pageIndex: Int): API<ListData<Article>> {
        val response = articleRepository.loadShareList(pageIndex)
        return API(response.errorCode, response.errorMsg, response.data?.shareArticles)
    }
}