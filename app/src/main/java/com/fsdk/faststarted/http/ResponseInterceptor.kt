package com.fsdk.faststarted.http

import android.text.TextUtils
import com.fsdk.faststarted.db.MyDatabase
import com.fsdk.faststarted.db.httpCache.HttpCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * 返回拦截器
 */
class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        val responseBody = response.body

        //判断是否启用缓存
        responseBody?.let {
            val url = request.url
            val header = request.header("cache")
            if (TextUtils.equals("true", header)) {
                if (response.code == 200) {
                    val data = responseBody.string()
                    //添加缓存
                    CoroutineScope(Dispatchers.IO).launch {
                        MyDatabase.daoHttpCache().setCacheData(
                            HttpCache(
                                cacheUrl = "$url",
                                cacheData = data,
                                cacheTime = "${System.currentTimeMillis()}"
                            )
                        )
                    }
                    //生成新的response
                    response = response.newBuilder()
                        .request(request)
                        .headers(request.headers)
                        .code(response.code)
                        .message(response.message)
                        .body(data.toResponseBody(responseBody.contentType()))
                        .build()
                } else {
                    //获取缓存
                    runBlocking(Dispatchers.IO) {
                        val httpCaches = MyDatabase.daoHttpCache().getCacheByUrl("$url")
                        if (httpCaches.isNotEmpty()) {
                            httpCaches[0].cacheData?.let {
                                //生成新的response
                                response = response.newBuilder()
                                    .request(request)
                                    .headers(request.headers)
                                    .code(response.code)
                                    .message(response.message)
                                    .body(it.toResponseBody(responseBody.contentType()))
                                    .build()
                            }
                        }
                    }
                }
            }
        }
        return response
    }
}