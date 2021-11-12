/**
 * Kotlin扩展属性和扩展函数
 */
package com.fsdk.faststarted.utils

import android.content.res.Resources
import android.util.TypedValue

//尺寸转换
val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Float.px: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        this,
        Resources.getSystem().displayMetrics
    )

val Float.sp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp: Float
    get() = this.toFloat().dp

val Int.px: Float
    get() = this.toFloat().px

val Int.sp: Float
    get() = this.toFloat().sp