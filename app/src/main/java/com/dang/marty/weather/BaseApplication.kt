package com.dang.marty.weather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

open class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}