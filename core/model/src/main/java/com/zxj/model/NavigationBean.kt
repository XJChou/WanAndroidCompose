package com.zxj.model

// 导航
data class NavigationBean(
    val articles: List<ArticleBean>,
    val cid: Int,
    val name: String
)