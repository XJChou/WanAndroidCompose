package com.zxj.model

import com.alibaba.fastjson.annotation.JSONField

// 收藏实体
data class CollectionBean(
    @JSONField(name = "id") val id: String,
    @JSONField(name = "chapterId") val chapterId: String,
    @JSONField(name = "chapterName") val chapterName: String,
    @JSONField(name = "author") val author: String,
    @JSONField(name = "desc") val desc: String,
    @JSONField(name = "courseId") val courseId: String,
    @JSONField(name = "envelopePic") val envelopePic: String,
    @JSONField(name = "link") val link: String,
    @JSONField(name = "niceDate") val niceDate: String,
    @JSONField(name = "origin") val origin: String,
    @JSONField(name = "originId") val originId: String,
    @JSONField(name = "publishTime") val publishTime: Long,
    @JSONField(name = "title") val title: String,
    @JSONField(name = "userId") val userId: String,
    @JSONField(name = "visible") val visible: Int,
    @JSONField(name = "zan") val zan: Int
)

val collectionBeanDemo by lazy {
    CollectionBean(
        id = "577",
        author = "鸿洋",
        chapterName = "开始动手实践",
        chapterId = "13",
        desc = "Carson带你学Android：手把手教你写一个完整的自定义View",
        courseId = "275110",
        envelopePic = "",
        origin = "",
        link = "https://www.jianshu.com/p/e9d8420b1b9c",
        niceDate = "6小时前",
        title = "",
        originId = "24169",
        publishTime = 1661590161000,
        userId = "131042",
        visible = 0,
        zan = 0
    )
}
