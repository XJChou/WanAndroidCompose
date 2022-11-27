package com.zxj.share

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.zxj.data.createIntPagingSource
import com.zxj.data.repository.RealArticleRepository
import com.zxj.model.API
import com.zxj.model.ArticleBean
import com.zxj.model.ListData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val articleRepository: RealArticleRepository
) : ViewModel() {

    private val page = Pager(PagingConfig(20)) {
        createIntPagingSource(fetch = ::fetch)
    }
    val pagingItems = page.flow.cachedIn(viewModelScope)
    val collectFlow = articleRepository.collectFlow


    private suspend fun fetch(pageIndex: Int): API<ListData<ArticleBean>> {
        val response = articleRepository.loadShareList(pageIndex)
        return API(response.errorCode, response.errorMsg, response.data?.shareArticles)
    }

    fun toggleCollect(collect: Boolean, articleBean: ArticleBean) {
        viewModelScope.launch {
            if (collect) {
                articleRepository.addCollectArticle(articleBean.id)
            } else {
                articleRepository.removeCollectArticle(articleBean.id)
            }
        }
    }
}