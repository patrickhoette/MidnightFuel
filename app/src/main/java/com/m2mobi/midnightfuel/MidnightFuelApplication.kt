package com.m2mobi.midnightfuel

import android.app.Application
import timber.log.Timber

class MidnightFuelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
