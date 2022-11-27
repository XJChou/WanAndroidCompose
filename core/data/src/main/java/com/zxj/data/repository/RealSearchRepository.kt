package com.zxj.data.repository

import com.zxj.data.module.asBean
import com.zxj.data.module.asEntity
import com.zxj.database.dao.HistorySearchDao
import com.zxj.model.API
import com.zxj.model.HistorySearchBean
import com.zxj.model.HotSearchBean
import com.zxj.network.datasource.SearchNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class RealSearchRepository @Inject constructor(
    private val searchNetworkDataSource: SearchNetworkDataSource,
    private val searchLocalDataSource: HistorySearchDao
) : SearchRepository {

    override suspend fun refreshHotSearchList(): API<List<HotSearchBean>> {
        return searchNetworkDataSource.loadHotSearchData()
            .ifSuspendSuccess {
                searchLocalDataSource.refreshHotSearchList(it?.map { it.asEntity() } ?: emptyList())
            }
    }

    override fun loadHotSearchList(): Flow<List<HotSearchBean>> {
        return searchLocalDataSource.queryHotSearchList().map {
            it.map { it.asBean() }
        }
    }


    override fun loadHistorySearchList(): Flow<List<HistorySearchBean>> {
        return searchLocalDataSource.queryHistorySearchList().map {
            it.map { it.asBean() }
        }
    }

    override suspend fun insertHistorySearch(search: String) {
        searchLocalDataSource.insertHistorySearch(
            HistorySearchBean(
                search,
                Date()
            ).asEntity()
        )
    }

    override suspend fun clearHistorySearch() {
        searchLocalDataSource.deleteAllHistorySearch()
    }

    override suspend fun deleteHistorySearch(item: HistorySearchBean) {
        searchLocalDataSource.deleteHistorySearch(item.asEntity())
    }
}