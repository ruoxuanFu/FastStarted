package com.fsdk.faststarted.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fsdk.faststarted.db.httpCache.HttpCache
import com.fsdk.faststarted.db.httpCache.HttpCacheDao
import com.fsdk.faststarted.utils.AppGlobal

@Database(entities = [HttpCache::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun httpCacheDao(): HttpCacheDao

    companion object {
        private val db: MyDatabase by lazy {
            Room
                .databaseBuilder(
                    AppGlobal.getApplication(),
                    MyDatabase::class.java,
                    "my-database-name"
                )
                //是否允许在主线程进行查询
                .allowMainThreadQueries()
                //数据库创建和打开后的回调
//                .addCallback()
                //设置查询的线程池
//                .setQueryExecutor()
//                .openHelperFactory()
                //room的日志模式
//                .setJournalMode()
                //数据库升级异常之后的回滚
//                .fallbackToDestructiveMigration()
                //数据库升级异常后根据指定版本进行回滚
//                .fallbackToDestructiveMigrationFrom()
                //升级数据库
//                .addMigrations(migration_1_2, migration_2_3)
                .build()
        }

//        private val migration_1_2: Migration by lazy {
//            object : Migration(1, 2) {
//                override fun migrate(database: SupportSQLiteDatabase) {
//                    database.execSQL("CREATE TABLE user (`user_id` INTEGER,`name` TEXT, PRIMARY KEY(`user_id`))")
//                }
//            }
//        }
//
//        private val migration_2_3: Migration by lazy {
//            object : Migration(2, 3) {
//                override fun migrate(database: SupportSQLiteDatabase) {
//                    database.execSQL("ALTER TABLE user ADD COLUMN age INTEGER")
//                }
//            }
//        }

        fun daoHttpCache(): HttpCacheDao {
            return db.httpCacheDao()
        }
    }

}