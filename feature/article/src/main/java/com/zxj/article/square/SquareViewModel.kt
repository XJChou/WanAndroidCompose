package com.zxj.article.square

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.zxj.data.createIntPagingSource
import com.zxj.data.repository.ArticleRepository
import com.zxj.model.API
import com.zxj.model.ArticleBean
import com.zxj.model.ListData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SquareViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val pager = Pager(PagingConfig(20)) { createIntPagingSource(::fetchData) }
    val pagingFlow = pager.flow.cachedIn(viewModelScope)

    private val collectMap = mutableStateMapOf<String, Boolean>()

    init {
        viewModelScope.launch {
            articleRepository.collectFlow.collect {
                collectMap[it.first] = it.second
            }
        }
    }

    fun getCollect(id: String): Boolean? = collectMap[id]

    fun articleCollect(collect: Boolean, articleBean: ArticleBean) {
        viewModelScope.launch {
            if (collect) {
                articleRepository.addCollectArticle(articleBean.id)
            } else {
                articleRepository.removeCollectArticle(articleBean.id)
            }
        }
    }

    private suspend fun fetchData(page: Int): API<ListData<ArticleBean>> {
        return articleRepository.loadSquareList(page)
    }
}