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
        appClass = this
        PreferenceManager.getInstance(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    companion object {
        private var appClass: IGrowApp? = null
        fun getInstance(): IGrowApp? {
            return appClass
        }
    }
}
