package com.fsdk.faststarted.ui.homepage

import com.fsdk.faststarted.http.BaseResponse
import com.fsdk.faststarted.http.HomePageData
import com.fsdk.faststarted.http.HttpUtil

class HomePageRepository {
    suspend fun getHomePageData(pageIndex: Int): BaseResponse<HomePageData> {
        return HttpUtil.service.getHomePageData(pageIndex = pageIndex)
    }
}