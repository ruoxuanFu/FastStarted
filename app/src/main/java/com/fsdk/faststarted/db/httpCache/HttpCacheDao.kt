package com.fsdk.faststarted.db.httpCache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//定义Dao
@Dao
interface HttpCacheDao {

    @Query("SELECT * FROM http_cache")
    suspend fun getAll(): List<HttpCache>

    @Query("SELECT * FROM http_cache WHERE cache_url LIKE :url")
    suspend fun getCacheByUrl(url: String): List<HttpCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCacheData(httpCache: HttpCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCacheData(httpCaches: List<HttpCache>)

    @Delete
    suspend fun deleteCache(httpCache: HttpCache)
}