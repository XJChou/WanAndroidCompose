package com.zxj.data.repository

import com.zxj.model.API
import com.zxj.model.HistorySearchBean
import com.zxj.model.HotSearchBean
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun loadHotSearchList(): Flow<List<HotSearchBean>>
    suspend fun refreshHotSearchList(): API<List<HotSearchBean>>

    fun loadHistorySearchList(): Flow<List<HistorySearchBean>>
    suspend fun insertHistorySearch(search: String)
    suspend fun clearHistorySearch()
    suspend fun deleteHistorySearch(item: HistorySearchBean)
}