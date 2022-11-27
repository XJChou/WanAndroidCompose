package com.zxj.model

import com.alibaba.fastjson.annotation.JSONField

data class UserBean(
    @JSONField(name = "id") val id: String,
    @JSONField(name = "icon") val icon: String,
    @JSONField(name = "username") val username: String,
    @JSONField(name = "email") val email: String,
    @JSONField(name = "type") val type: Int,
    @JSONField(name = "chapterTops") val chapterTops: List<String>?,
    @JSONField(name = "collectIds") val collectIds: List<String>?
)

val USER_EMPTY = UserBean(
    "", "", "", "",
    0, null, null
)

val userBeanDemo = UserBean(
    id = "",
    icon = "",
    username = "userBeanDemo",
    email = "",
    type = 0,
    chapterTops = null,
    collectIds = null
)