package com.zxj.model

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