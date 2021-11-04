package com.fsdk.faststarted

import android.app.Application
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.fsdk.faststarted.utils.LocalStorage

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //log
        val config = LogConfiguration.Builder()
            .tag("fLogger")
            .disableStackTrace()
            .logLevel(LogLevel.ALL)
            .disableThreadInfo()
            .disableBorder()
            .build()
        XLog.init(config)

        //本地存储
        LocalStorage.init(this)
    }
}