package com.zxj.model

import com.alibaba.fastjson.annotation.JSONField

/**
 * 我的分享
 */
data class MineShareBean(
    @JSONField(name = "coinInfo") val rankBean: RankBean,
    val shareArticles: ListData<ArticleBean>
)

val mineShareBean by lazy {
    MineShareBean(
        rankBean = rankBeanDemo,
        shareArticles = ListData(0, datas = listOf(articleBeanDemo), 0, false, 0, 0, 0)
    )
}