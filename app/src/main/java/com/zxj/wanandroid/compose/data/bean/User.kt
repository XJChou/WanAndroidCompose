package com.zxj.wanandroid.compose.data.bean

import com.alibaba.fastjson.annotation.JSONField

data class User(
    @JSONField(name = "id") val id: String = "",
    @JSONField(name = "icon") val icon: String = "",
    @JSONField(name = "username") val username: String = "",
    @JSONField(name = "email") val email: String = "",
    @JSONField(name = "type") val type: Int = 0,
    @JSONField(name = "chapterTops") val chapterTops: MutableList<String>? = null,
    @JSONField(name = "collectIds") val collectIds: MutableList<String>? = null
)
