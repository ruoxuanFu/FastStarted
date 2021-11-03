package com.fsdk.faststarted.utils.coroutineUtil

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 * 协程错误处理逻辑
 * @property errCode Int 错误码
 * @property errMsg String 简要错误信息
 * @property report Boolean 是否需要上报
 * @constructor
 */
class GlobalCoroutineExceptionHandler(
    private val errCode: Int,
    private val errMsg: String = "",
    private val report: Boolean = false
) : CoroutineExceptionHandler {

    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Log.e(
            "协程捕获错误", "exception: ${exception.stackTraceToString()}," +
                    " errCode: $errCode, errMsg: $errMsg, report: $report"
        )
    }
}