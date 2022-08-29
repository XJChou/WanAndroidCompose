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


// demo数据
val collectionArticleDemoData by lazy {
    CollectionArticle(
        author = "鸿洋", 577, "开始动手实践", 13, "",
        "", 275110, "https://www.jianshu.com/p/e9d8420b1b9c", "6小时前", "",
        24169, 1661590161000, "Carson带你学Android：手把手教你写一个完整的自定义View", 131042, 0, 0
    )
}