package com.zxj.model

import com.alibaba.fastjson.annotation.JSONField

// 公众号列表实体
data class WechatChapterBean(
    @JSONField(name = "id") val id: Int,
    @JSONField(name = "children") val children: List<String>,
    @JSONField(name = "courseId") val courseId: Int,
    @JSONField(name = "name") val name: String,
    @JSONField(name = "order") val order: Int,
    @JSONField(name = "parentChapterId") val parentChapterId: Int,
    @JSONField(name = "userControlSetTop") val userControlSetTop: Boolean,
    @JSONField(name = "visible") val visible: Int
)