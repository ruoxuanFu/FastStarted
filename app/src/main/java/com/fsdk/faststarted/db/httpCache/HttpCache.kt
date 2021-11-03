package com.fsdk.faststarted.db.httpCache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

//定义表
@Entity(tableName = "http_cache")
data class HttpCache(
    //定义列名
    //列名默认为变量名称，使用@ColumnInfo指定设置列名的别名，
    //使用@PrimaryKey标记主键
    //autoGenerate = true使主键分配自动id
//    @PrimaryKey(autoGenerate = true)
//    val id: Int,

    //缓存url
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cache_url")
    var cacheUrl: String = "",

    //缓存数据，统一转换为String存储
    @ColumnInfo(name = "cache_response")
    var cacheData: String? = "",

    //缓存数据，统一转换为String存储
    @ColumnInfo(name = "cache_time")
    var cacheTime: String? = "",

    //使用@Ignore标记的变量不会被存储在数据库中
    @Ignore
    var save: Boolean = false,
)