package com.zxj.model

import com.alibaba.fastjson.annotation.JSONField
import java.util.*

// 个人积分实体
data class UserScoreBean(
    @JSONField(name = "id") val id: String,
    @JSONField(name = "userId") val userId: String,
    @JSONField(name = "userName") val userName: String,
    @JSONField(name = "coinCount") val coinCount: Int,
    @JSONField(name = "date") val date: Long,
    @JSONField(name = "desc") val desc: String,
    @JSONField(name = "reason") val reason: String,
    @JSONField(name = "type") val type: Int,
)

val userScoreBeanDemo by lazy {
    UserScoreBean(
        id = "1",
        userId = "1",
        userName = "username",
        date = Date().time,
        desc = "desc",
        reason = "reason",
        type = 1,
        coinCount = 1
    )
}