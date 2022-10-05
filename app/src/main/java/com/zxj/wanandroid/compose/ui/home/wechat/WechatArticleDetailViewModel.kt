package com.zxj.wanandroid.compose.ui.home.wechat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.zxj.wanandroid.compose.data.bean.Article
import com.zxj.wanandroid.compose.data.bean.ListData
import com.zxj.wanandroid.compose.data.repositories.ArticleRepository
import com.zxj.wanandroid.compose.net.API
import com.zxj.wanandroid.compose.utils.createIntPagingSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WechatArticleDetailViewModel @AssistedInject constructor(
    @Assisted private val cid: Int,
    private val articleRepository: ArticleRepository
) : ViewModel() {

    init {
        println("WechatArticleDetailViewModel init cid = ${cid}")
    }

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

    private val collectFlow =
        articleRepository.collectFlow.stateIn(viewModelScope, SharingStarted.Lazily, null)
    private val pager = Pager(PagingConfig(20)) { createIntPagingSource(::fetch) }
    val pagingData = pager.flow.cachedIn(viewModelScope)
        .combine(collectFlow) { pagingData, collect ->
            pagingData.map {
                if (it.id == collect?.first) {
                    it.collect = collect.second
                }
                it
            }
        }

    private suspend fun fetch(page: Int): API<ListData<Article>> {
        return articleRepository.loadKnowledgeList(page, cid)
    }

    fun collectArticle(collect: Boolean, id: Int) {
        viewModelScope.launch {
            if (collect) {
                articleRepository.addCollectArticle(id)
            } else {
                articleRepository.removeCollectArticle(id)
            }
        }
    }

}