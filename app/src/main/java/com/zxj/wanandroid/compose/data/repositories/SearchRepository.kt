package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.bean.HistorySearchBean
import com.zxj.wanandroid.compose.data.bean.HotSearchBean
import com.zxj.wanandroid.compose.net.API
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun loadHotSearchList(): Flow<List<HotSearchBean>>
    suspend fun refreshHotSearchList(): API<List<HotSearchBean>>

    fun loadHistorySearchList(): Flow<List<HistorySearchBean>>
    suspend fun insertHistorySearch(search: String)
    suspend fun clearHistorySearch()
    suspend fun deleteHistorySearch(item: HistorySearchBean)
}