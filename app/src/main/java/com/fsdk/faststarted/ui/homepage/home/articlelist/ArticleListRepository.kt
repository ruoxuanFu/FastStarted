package com.fsdk.faststarted.ui.homepage.home.articlelist

import com.fsdk.faststarted.http.BaseResponse
import com.fsdk.faststarted.http.HomePageData
import com.fsdk.faststarted.http.HttpUtil

class ArticleListRepository {
    suspend fun getArticleListData(pageIndex: Int): BaseResponse<HomePageData> {
        return HttpUtil.service.getHomePageData(pageIndex = pageIndex)
    }
}