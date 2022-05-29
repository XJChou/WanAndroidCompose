package com.zxj.wanandroid.compose.data

data class ArticleBean(
    val curPage: Int,
    val datas: List<Data>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class Data(
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
    companion object {
        val Demo = Data(
            "",
            1,
            "",
            false,
            502,
            "自助",
            false,
            13,
            "",
            "",
            "",
            "",
            true,
            "",
            22843,
            "https://juejin.cn/post/7101600959456870437",
            "18小时前",
            "18小时前",
            "",
            "",
            "",
            1653639991000,
            493,
            0,
            1653639991000,
            "345丶",
            494,
            "广场Tab",
            arrayListOf(),
            "Android | Compsoe 初上手",
            0,
            70343,
            1,
            0
        )
    }
}

data class Tag(
    val name: String,
    val url: String
)