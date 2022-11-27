package com.zxj.data

import androidx.paging.*
import com.zxj.model.API
import com.zxj.model.ListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

fun <Item : Any> createIntPagingSource(fetch: suspend (Int) -> API<ListData<Item>>): PagingSource<Int, Item> {
    return IntPagingSource(fetch)
}

fun <Item : Any> CoroutineScope.createIntPagingFlow(fetch: suspend (Int) -> API<ListData<Item>>): Flow<PagingData<Item>> {
    val pager = Pager(PagingConfig(20)) { createIntPagingSource(fetch) }
    return pager.flow.cachedIn(this)
}

private class IntPagingSource<Item : Any>(val fetch: suspend (Int) -> API<ListData<Item>>) :
    PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val pageIndex = params.key ?: 1
        val response = fetch(pageIndex)
        return if (response.isSuccess) {
            val prevKey = if (pageIndex == 1) null else pageIndex.minus(1)
            val dataList = response.data?.datas ?: emptyList()
            val nextKey =
                if (dataList.isEmpty() || response.data?.over == true) null else pageIndex.plus(1)
            LoadResult.Page(dataList, prevKey, nextKey)
        } else {
            LoadResult.Error(RuntimeException(response.errorMsg))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}