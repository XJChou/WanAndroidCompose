package com.zxj.database.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

// 热门搜索
@Keep
@Entity(tableName = "hot_search")
data class HotSearchEntity(
    @PrimaryKey val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)