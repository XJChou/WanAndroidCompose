package com.zxj.database.dao

import androidx.room.*
import com.zxj.database.model.HistorySearchEntity
import com.zxj.database.model.HotSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorySearchDao {

    @Query("select * from history_search order by time desc")
    fun queryHistorySearchList(): Flow<List<HistorySearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistorySearch(historySearchBean: HistorySearchEntity)

    @Query("delete from history_search")
    suspend fun deleteAllHistorySearch()

    @Delete
    suspend fun deleteHistorySearch(item: HistorySearchEntity)


    @Query("select * from hot_search order by `order`")
    fun queryHotSearchList(): Flow<List<HotSearchEntity>>

    @Insert
    suspend fun insertHotSearchList(list: List<HotSearchEntity>)

    @Query("delete from hot_search")
    suspend fun deleteAllHotSearchList()

    @Transaction
    suspend fun refreshHotSearchList(list: List<HotSearchEntity>) {
        deleteAllHotSearchList()
        insertHotSearchList(list)
    }
}