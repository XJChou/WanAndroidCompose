package com.zxj.model

import com.alibaba.fastjson.annotation.JSONField

/**
 * 排行榜实体
 * @param rank 排名
 * @param userId 用户id
 * @param username 用户名
 * @param coinCount 积分数
 * @param level 等级
 */
data class RankBean(
    @JSONField(name = "rank") val rank: Int,
    @JSONField(name = "userId") val userId: String,
    @JSONField(name = "username") val username: String,
    @JSONField(name = "coinCount") val coinCount: Int,
    @JSONField(name = "level") val level: Int,
)

val rankBeanDemo by lazy {
    RankBean(userId = "1", rank = 2, coinCount = 3, level = 4, username = "username")
}
