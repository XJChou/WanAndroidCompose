package com.zxj.database.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * 搜索记录
 */
@Keep
@Entity(tableName = "history_search")
data class HistorySearchEntity(
    @PrimaryKey val search: String,
    val time: Date,
)