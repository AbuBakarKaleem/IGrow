package com.app.igrow

import android.app.Application
import android.content.Context
import com.app.igrow.utils.LocaleHelper
import com.app.igrow.utils.PreferenceManager
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class IGrowApp : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceManager.getInstance(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    init {
        instance = this
    }

    companion object {
        private var instance: IGrowApp? = null
        fun getInstance(): IGrowApp {
            synchronized(IGrowApp::class.java) {
                if (instance == null)
                    instance =
                        IGrowApp()

            }
            return instance!!
        }
    }

}
