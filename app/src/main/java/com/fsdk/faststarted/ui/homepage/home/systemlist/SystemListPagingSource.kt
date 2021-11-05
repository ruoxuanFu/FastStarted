package com.fsdk.faststarted.ui.homepage.home.systemlist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elvishew.xlog.XLog
import com.fsdk.faststarted.bean.SystemListData
import com.fsdk.faststarted.http.ApiService
import kotlinx.coroutines.delay

class SystemListPagingSource(private val apiService: ApiService) :
    PagingSource<Int, SystemListData>() {

    override fun getRefreshKey(state: PagingState<Int, SystemListData>): Int? {
        // 尝试从 prevKey 或 nextKey 中找到与 anchorPosition 最接近的页面的页面键，
        // 但仍需要在此处理key可空性：
        // prevKey == null -> anchorPage 是第一页
        // nextKey == null -> anchorPage 是最后一页
        // prevKey == nextKey == null -> anchorPage 是初始页面，因此只需返回null
        return state.anchorPosition?.let {
            //获取最接近列表中最后访问索引的加载页面
            val anchorPage = state.closestPageToPosition(it)
            //判断加载方向
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SystemListData> {
        try {
            delay(5000)
            //如果未定义，则从第 1 页开始刷新。
            val nextPageNumber = params.key ?: 1
            val responseList = mutableListOf<SystemListData>()
            for (i in 1..20) {
                responseList.add(
                    SystemListData(
                        id = nextPageNumber * 100 + i,
//                        id = "${nextPageNumber * 100}+$i".toInt(),
                        name = "学科$nextPageNumber - $i",
                        desc = "描述 $nextPageNumber - $i"
                    )
                )
            }
            return LoadResult.Page(
                data = responseList,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}