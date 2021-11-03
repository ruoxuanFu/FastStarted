package com.fsdk.faststarted.utils

import android.app.Application
import android.os.Parcelable
import com.fsdk.faststarted.constant.LocalStorageKey
import com.tencent.mmkv.MMKV

/**
 * 本地存储
 * 代替SharedPreferences
 */
object LocalStorage {
    fun init(application: Application) {
        MMKV.initialize(application)
    }

    fun putBoolean(@LocalStorageKey key: String, value: Boolean) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun getBoolean(@LocalStorageKey key: String, defaultValue: Boolean = false): Boolean {
        return MMKV.defaultMMKV().decodeBool(key, defaultValue)
    }

    fun putString(@LocalStorageKey key: String, value: String) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun getString(@LocalStorageKey key: String, defaultValue: String = ""): String {
        return MMKV.defaultMMKV().decodeString(key, defaultValue)!!
    }

    fun putInt(@LocalStorageKey key: String, value: Int) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun getInt(@LocalStorageKey key: String, defaultValue: Int = 0): Int {
        return MMKV.defaultMMKV().decodeInt(key, defaultValue)
    }

    fun putFloat(@LocalStorageKey key: String, value: Float) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun getFloat(@LocalStorageKey key: String, defaultValue: Float = 0F): Float {
        return MMKV.defaultMMKV().decodeFloat(key, defaultValue)
    }

    fun putLong(@LocalStorageKey key: String, value: Long) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun getLong(@LocalStorageKey key: String, defaultValue: Long = 0L): Long {
        return MMKV.defaultMMKV().decodeLong(key, defaultValue)
    }

    fun putDouble(@LocalStorageKey key: String, value: Double) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun getDouble(@LocalStorageKey key: String, defaultValue: Double = 0.0): Double {
        return MMKV.defaultMMKV().decodeDouble(key, defaultValue)
    }

    fun putByteArray(@LocalStorageKey key: String, value: ByteArray) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun getByteArray(
        @LocalStorageKey key: String,
        defaultValue: ByteArray = ByteArray(0)
    ): ByteArray {
        return MMKV.defaultMMKV().decodeBytes(key, defaultValue)!!
    }

    fun putStringSet(@LocalStorageKey key: String, value: Set<String>) {
        MMKV.defaultMMKV().encode(key, value)
    }

    fun getStringSet(
        @LocalStorageKey key: String,
        defaultValue: Set<String> = mutableSetOf()
    ): Set<String> {
        return MMKV.defaultMMKV().decodeStringSet(key, defaultValue)!!
    }

    fun putParcelable(@LocalStorageKey key: String, value: Parcelable) {
        MMKV.defaultMMKV().encode(key, value)
    }

    inline fun <reified T : Parcelable> getParcelable(@LocalStorageKey key: String): T? {
        return MMKV.defaultMMKV().decodeParcelable(key, T::class.java)
    }

    fun removeValueForKey(@LocalStorageKey key: String) {
        MMKV.defaultMMKV().removeValueForKey(key)
    }

    fun containsKey(@LocalStorageKey key: String): Boolean {
        return MMKV.defaultMMKV().containsKey(key)
    }
}