package com.zxj.data.module

import com.zxj.database.model.HotSearchEntity
import com.zxj.model.HotSearchBean

fun HotSearchBean.asEntity(): HotSearchEntity {
    return HotSearchEntity(
        id = id,
        link = link,
        name = name,
        order = order,
        visible = visible
    )
}

fun HotSearchEntity.asBean(): HotSearchBean {
    return HotSearchBean(
        id, link, name, order, visible
    )
}
