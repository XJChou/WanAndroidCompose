package com.zxj.model

data class BannerBean(
    val id: String,
    val url: String,
    val desc: String,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
)
