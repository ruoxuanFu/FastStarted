package com.fsdk.faststarted.utils

import android.annotation.SuppressLint
import android.app.Application

object AppGlobal {

    private lateinit var sApplication: Application

    /**
     * 通过反射获取全局的Application
     *
     * @return Application
     */
    @SuppressLint("DiscouragedPrivateApi", "PrivateApi")
    fun getApplication(): Application {
        if (!::sApplication.isInitialized) {
            try {
                val currentApplication = Class.forName("android.app.ActivityThread")
                    .getDeclaredMethod("currentApplication")
                currentApplication.isAccessible = true
                sApplication = currentApplication.invoke(null) as Application
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return sApplication
    }

}