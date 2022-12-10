package com.zxj.network.datasource

import com.zxj.model.API
import com.zxj.model.HotSearchBean
import retrofit2.http.GET

interface SearchNetworkDataSource {

    @GET("hotkey/json")
    suspend fun loadHotSearchData(): API<List<HotSearchBean>>
}