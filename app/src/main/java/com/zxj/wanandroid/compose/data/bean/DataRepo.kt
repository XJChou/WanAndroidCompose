package com.zxj.wanandroid.compose.data.bean

import com.alibaba.fastjson.annotation.JSONField
import java.util.*

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

// 用户个人信息
data class UserInfoBody(
    @JSONField(name = "coinCount") val coinCount: Int, // 总积分
    @JSONField(name = "rank") val rank: Int, // 当前排名
    @JSONField(name = "userId") val userId: Int,
    @JSONField(name = "username") val username: String
)

// 个人积分实体
data class UserScoreBean(
    @JSONField(name = "coinCount") val coinCount: Int,
    @JSONField(name = "date") val date: Long,
    @JSONField(name = "desc") val desc: String,
    @JSONField(name = "id") val id: Int,
    @JSONField(name = "reason") val reason: String,
    @JSONField(name = "type") val type: Int,
    @JSONField(name = "userId") val userId: Int,
    @JSONField(name = "userName") val userName: String
)

val userScoreBeanDemoData by lazy {
    UserScoreBean(
        1, Date().time, "desc", 1,
        "reason", 1, 1, "username"
    )
}


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