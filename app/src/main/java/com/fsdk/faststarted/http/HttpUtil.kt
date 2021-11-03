package com.fsdk.faststarted.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object HttpUtil {

    val service: ApiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        buildApiService()
    }

    private val client: OkHttpClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        buildClient()
    }

    /**
     * 初始化Retrofit
     * @return ApiService
     */
    private fun buildApiService(): ApiService {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(HttpConstant.baseUrl)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    /**
     * 初始化okhttp3
     * @return OkHttpClient
     */
    private fun buildClient(): OkHttpClient {
        //初始日志拦截器
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient
            .Builder()
            .connectTimeout(HttpConstant.defaultTimeout, TimeUnit.SECONDS)
            //请求拦截器
            .addInterceptor(RequestInterceptor())
            //返回拦截器
            .addInterceptor(ResponseInterceptor())
            //okhttp3日志拦截器
            .addInterceptor(logging)
            .followSslRedirects(true)
            .build()
    }

}