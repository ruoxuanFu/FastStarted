package com.fsdk.faststarted.http

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("errorCode")
    val code: Int,
    @SerializedName("errorMsg")
    val msg: String? = null,
    @SerializedName("data")
    val data: T?
)