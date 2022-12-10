package com.zxj.article.system

import android.net.Uri
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.alibaba.fastjson.JSONObject
import com.zxj.article.navigation.knowledgeSystemDetailKnowledgesArgs
import com.zxj.article.navigation.knowledgeSystemDetailNameArgs
import com.zxj.data.createIntPagingSource
import com.zxj.data.repository.ArticleRepository
import com.zxj.model.ArticleBean
import com.zxj.model.Knowledge
import com.zxj.ui.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KnowledgeSystemDetailViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val name: String = checkNotNull(savedStateHandle[knowledgeSystemDetailNameArgs])
    val knowledges: List<Knowledge> = checkNotNull(
        JSONObject.parseArray(
            Uri.decode(savedStateHandle[knowledgeSystemDetailKnowledgesArgs]),
            Knowledge::class.java
        )
    )

    private val collectMap = mutableStateMapOf<String, Boolean>()

    fun getCollect(id: String): Boolean? = collectMap[id]

    private val pagingItemsCache = HashMap<Int, Flow<PagingData<ArticleBean>>>()

    fun getPagingItems(cid: Int): Flow<PagingData<ArticleBean>> {
        if (!pagingItemsCache.containsKey(cid)) {
            pagingItemsCache.put(cid, Pager(PagingConfig(20)) {
                createIntPagingSource { fetchData(cid, it) }
            }.flow.cachedIn(viewModelScope))
        }
        return checkNotNull(pagingItemsCache[cid])
    }

    private suspend fun fetchData(cid: Int, page: Int) =
        articleRepository.loadKnowledgeList(page, cid)

    fun collectArticle(collect: Boolean, articleBean: ArticleBean) {
        viewModelScope.launch {
            if (collect) {
                articleRepository.addCollectArticle(articleBean.id)
            } else {
                articleRepository.removeCollectArticle(articleBean.id)
            }
        }

    }

    init {
        viewModelScope.launch {
            articleRepository.collectFlow.collect {
                collectMap[it.first] = it.second
            }
        }
    }
}