package com.app.igrow

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IGrowApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }

}
