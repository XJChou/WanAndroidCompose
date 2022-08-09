package com.zxj.wanandroid.compose.data.datasource

import com.zxj.wanandroid.compose.data.bean.HotSearchBean
import com.zxj.wanandroid.compose.net.API
import retrofit2.http.GET

interface SearchNetworkDataSource {

    @GET("hotkey/json")
    suspend fun loadHotSearchData(): API<List<HotSearchBean>>
}