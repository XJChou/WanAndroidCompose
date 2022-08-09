package com.zxj.wanandroid.compose.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alibaba.fastjson.annotation.JSONField

// 热门搜索
@Entity(tableName = "hot_search")
data class HotSearchBean(
    @PrimaryKey @JSONField(name = "id") val id: Int,
    @JSONField(name = "link") val link: String,
    @JSONField(name = "name") val name: String,
    @JSONField(name = "order") val order: Int,
    @JSONField(name = "visible") val visible: Int
)