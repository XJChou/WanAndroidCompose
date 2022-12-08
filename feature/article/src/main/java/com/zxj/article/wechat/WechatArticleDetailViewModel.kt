package com.zxj.article.wechat

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.zxj.data.createIntPagingSource
import com.zxj.data.repository.ArticleRepository
import com.zxj.model.API
import com.zxj.model.ArticleBean
import com.zxj.model.ListData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WechatArticleDetailViewModel @AssistedInject constructor(
    @Assisted private val cid: Int,
    private val articleRepository: ArticleRepository
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(cid: Int): WechatArticleDetailViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            cid: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(cid) as T
            }
        }
    }

    private val collectMap = mutableStateMapOf<String, Boolean>()
    private val pager = Pager(PagingConfig(20)) { createIntPagingSource(::fetch) }
    val pagingData = pager.flow.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            articleRepository.collectFlow.collect {
                collectMap[it.first] = it.second
            }
        }
    }

    private suspend fun fetch(page: Int): API<ListData<ArticleBean>> {
        return articleRepository.loadKnowledgeList(page, cid)
    }

    fun getCollect(id: String): Boolean? = collectMap[id]

    fun collectArticle(collect: Boolean, id: String) {
        viewModelScope.launch {
            if (collect) {
                articleRepository.addCollectArticle(id)
            } else {
                articleRepository.removeCollectArticle(id)
            }
        }
    }

}