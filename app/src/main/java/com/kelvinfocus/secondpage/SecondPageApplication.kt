package com.kelvinfocus.secondpage

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SecondPageApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Timber init
        Timber.plant(Timber.DebugTree())
    }
}