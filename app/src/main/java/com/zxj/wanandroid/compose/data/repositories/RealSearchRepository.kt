package com.zxj.wanandroid.compose.data.repositories

import com.zxj.wanandroid.compose.data.bean.HistorySearchBean
import com.zxj.wanandroid.compose.data.bean.HotSearchBean
import com.zxj.wanandroid.compose.data.datasource.SearchLocalDataSource
import com.zxj.wanandroid.compose.data.datasource.SearchNetworkDataSource
import com.zxj.wanandroid.compose.net.API
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class RealSearchRepository @Inject constructor(
    private val searchNetworkDataSource: SearchNetworkDataSource,
    private val searchLocalDataSource: SearchLocalDataSource
) : SearchRepository {

    override suspend fun refreshHotSearchList(): API<List<HotSearchBean>> {
        return searchNetworkDataSource.loadHotSearchData()
            .ifSuspendSuccess {
                searchLocalDataSource.refreshHotSearchList(it ?: emptyList())
            }
    }

    override fun loadHotSearchList(): Flow<List<HotSearchBean>> {
        return searchLocalDataSource.queryHotSearchList()
    }


    override fun loadHistorySearchList(): Flow<List<HistorySearchBean>> {
        return searchLocalDataSource.queryHistorySearchList()
    }

    override suspend fun insertHistorySearch(search: String) {
        searchLocalDataSource.insertHistorySearch(HistorySearchBean(search, Date()))
    }

    override suspend fun clearHistorySearch() {
        searchLocalDataSource.deleteAllHistorySearch()
    }

    override suspend fun deleteHistorySearch(item: HistorySearchBean) {
        searchLocalDataSource.deleteHistorySearch(item)
    }
}