package com.zxj.wanandroid.compose.data.datasource

import androidx.room.*
import com.zxj.wanandroid.compose.data.bean.HistorySearchBean
import com.zxj.wanandroid.compose.data.bean.HotSearchBean
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchLocalDataSource {

    @Query("select * from history_search order by time desc")
    fun queryHistorySearchList(): Flow<List<HistorySearchBean>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistorySearch(historySearchBean: HistorySearchBean)

    @Query("delete from history_search")
    suspend fun deleteAllHistorySearch()

    @Delete
    suspend fun deleteHistorySearch(item: HistorySearchBean)


    @Query("select * from hot_search order by `order`")
    fun queryHotSearchList(): Flow<List<HotSearchBean>>

    @Insert
    suspend fun insertHotSearchList(list: List<HotSearchBean>)

    @Query("delete from hot_search")
    suspend fun deleteAllHotSearchList()

    @Transaction
    suspend fun refreshHotSearchList(list: List<HotSearchBean>) {
        deleteAllHotSearchList()
        insertHotSearchList(list)
    }
}