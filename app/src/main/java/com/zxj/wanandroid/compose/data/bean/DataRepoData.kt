package com.zxj.wanandroid.compose.data.bean

import java.util.*

val userScoreBeanDemoData by lazy {
    UserScoreBean(
        1, Date().time, "desc", 1,
        "reason", 1, 1, "username"
    )
}

val collectionArticleDemoData by lazy {
    CollectionArticle(
        author = "鸿洋", 577, "开始动手实践", 13, "",
        "", 275110, "https://www.jianshu.com/p/e9d8420b1b9c", "6小时前", "",
        24169, 1661590161000, "Carson带你学Android：手把手教你写一个完整的自定义View", 131042, 0, 0
    )
}

val coinInfoBeanDemoData by lazy {
    CoinInfoBean(1, 2, 3, 4, "username")
}

val articleBeanDemoData by lazy {
    Article(
        "",
        1,
        "",
        false,
        502,
        "自助",
        false,
        13,
        "",
        "",
        "",
        "",
        true,
        "",
        22843,
        "https://juejin.cn/post/7101600959456870437",
        "18小时前",
        "18小时前",
        "",
        "",
        "",
        1653639991000,
        493,
        0,
        1653639991000,
        "345丶",
        494,
        "广场Tab",
        arrayListOf(),
        "Android | Compsoe 初上手",
        0,
        70343,
        1,
        0
    )
}

val defaultBack: () -> Unit = {}

val defaultArticleItemClick: (Article) -> Unit = {}

val defaultArticleCollectClick: (Boolean, Article) -> Unit = { _, _ -> }

val defaultRefresh: () -> Unit = {}
val defaultLoad: () -> Unit = {}