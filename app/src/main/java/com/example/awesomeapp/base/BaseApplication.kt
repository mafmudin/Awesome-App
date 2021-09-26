package com.example.awesomeapp.base

import android.app.Application
import com.example.awesomeapp.BuildConfig
import timber.log.Timber

open class BaseApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}