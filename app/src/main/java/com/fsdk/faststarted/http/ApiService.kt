package com.fsdk.faststarted.http

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("article/list/{pageIndex}/json?page_size=11")
    suspend fun getHomePageData(
        @Path("pageIndex") pageIndex: Int,
        @Header("cache") cache: String = "true"
    ): BaseResponse<HomePageData>
}