package com.zxj.wanandroid.compose.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

/**
 * 搜索记录
 */
@Entity(tableName = "history_search")
data class HistorySearchBean(
    @PrimaryKey val search: String,
    val time: Date,
)