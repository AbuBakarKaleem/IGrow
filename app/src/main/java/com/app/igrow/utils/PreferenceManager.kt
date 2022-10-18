package com.app.igrow.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.app.igrow.IGrowApp
import com.app.igrow.utils.Constants.currentLanguage


class PreferenceManager(context: Context?) {

    init {
        pref = if (context != null) {
            PreferenceManager.getDefaultSharedPreferences(context)
        } else {
            IGrowApp.getInstance()?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        }
    }

    companion object{
        private var instance: com.app.igrow.utils.PreferenceManager? = null
        private var pref: SharedPreferences? = null

        fun getInstance(context: Context?): com.app.igrow.utils.PreferenceManager? {
            if (instance == null || pref == null) {
                instance = PreferenceManager(context)
            }
            return instance
        }
    }

    fun getLanguage(): String? {
        return pref!!.getString(currentLanguage, "en")
    }

    fun setLanguage(b: String?) {
        pref!!.edit().putString(currentLanguage, b).apply()
    }
}