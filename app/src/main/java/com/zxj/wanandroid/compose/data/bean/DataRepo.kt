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

// 文章相关
data class Article(
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val top:String?,
    val fresh: Boolean,
    val host: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Tag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
) {
    data class Tag(
        val name: String,
        val url: String
    )
}


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

// 收藏实体
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

// 排行榜实体
data class CoinInfoBean(
    @JSONField(name = "coinCount") val coinCount: Int,
    @JSONField(name = "level") val level: Int,
    @JSONField(name = "rank") val rank: Int,
    @JSONField(name = "userId") val userId: Int,
    @JSONField(name = "username") val username: String
)

// 我的分享
data class ShareResponseBody(
    val coinInfo: CoinInfoBean,
    val shareArticles: ListData<Article>
)
