package com.zxj.data.module

import com.zxj.database.model.HistorySearchEntity
import com.zxj.model.HistorySearchBean

fun HistorySearchEntity.asBean(): HistorySearchBean {
    return HistorySearchBean(search, time)
}

fun HistorySearchBean.asEntity(): HistorySearchEntity {
    return HistorySearchEntity(search, time)
}
