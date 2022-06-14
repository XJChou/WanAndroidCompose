package com.zxj.wanandroid.compose.data.bean

import com.alibaba.fastjson.annotation.JSONField

data class LoginBean(
    @JSONField(name = "id") val id: Int,
    @JSONField(name = "icon") val icon: String,
    @JSONField(name = "username") val username: String,
    @JSONField(name = "password") val password: String,
    @JSONField(name = "email") val email: String,
    @JSONField(name = "token") val token: String,
    @JSONField(name = "type") val type: Int,
    @JSONField(name = "chapterTops") val chapterTops: MutableList<String>,
    @JSONField(name = "collectIds") val collectIds: MutableList<String>
)
