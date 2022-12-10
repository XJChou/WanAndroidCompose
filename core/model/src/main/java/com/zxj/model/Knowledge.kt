package com.zxj.model

import com.alibaba.fastjson.annotation.JSONField
import java.io.Serializable

//知识体系
data class KnowledgeTreeBean(
    @JSONField(name = "children") val children: List<Knowledge>,
    @JSONField(name = "courseId") val courseId: Int,
    @JSONField(name = "id") val id: Int,
    @JSONField(name = "name") val name: String,
    @JSONField(name = "order") val order: Int,
    @JSONField(name = "parentChapterId") val parentChapterId: Int,
    @JSONField(name = "visible") val visible: Int
)


data class Knowledge(
    @JSONField(name = "children") val children: List<Any>,
    @JSONField(name = "courseId") val courseId: Int,
    @JSONField(name = "id") val id: Int,
    @JSONField(name = "name") val name: String,
    @JSONField(name = "order") val order: Int,
    @JSONField(name = "parentChapterId") val parentChapterId: Int,
    @JSONField(name = "visible") val visible: Int
) : Serializable
