package com.zxj.wanandroid.compose.data.bean

import com.alibaba.fastjson.annotation.JSONField

// 通用的带有列表数据的实体
data class ListData<T>(
    @JSONField(name = "curPage") val curPage: Int,
    @JSONField(name = "datas") val datas: List<T>,
    @JSONField(name = "offset") val offset: Int,
    @JSONField(name = "over") val over: Boolean,
    @JSONField(name = "pageCount") val pageCount: Int,
    @JSONField(name = "size") val size: Int,
    @JSONField(name = "total") val total: Int
)

data class CollectionArticle(
    @JSONField(name = "author") val author: String,
    @JSONField(name = "chapterId") val chapterId: Int,
    @JSONField(name = "chapterName") val chapterName: String,
    @JSONField(name = "courseId") val courseId: Int,
    @JSONField(name = "desc") val desc: String,
    @JSONField(name = "envelopePic") val envelopePic: String,
    @JSONField(name = "id") val id: Int,
    @JSONField(name = "link") val link: String,
    @JSONField(name = "niceDate") val niceDate: String,
    @JSONField(name = "origin") val origin: String,
    @JSONField(name = "originId") val originId: Int,
    @JSONField(name = "publishTime") val publishTime: Long,
    @JSONField(name = "title") val title: String,
    @JSONField(name = "userId") val userId: Int,
    @JSONField(name = "visible") val visible: Int,
    @JSONField(name = "zan") val zan: Int
)