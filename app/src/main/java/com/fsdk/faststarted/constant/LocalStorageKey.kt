package com.fsdk.faststarted.constant

import androidx.annotation.StringDef

/**
 * 本地存储Key值
 */
@StringDef(LocalStorageKey.default)
@Retention(AnnotationRetention.SOURCE)
annotation class LocalStorageKey {
    companion object {
        const val default = "default"
    }
}