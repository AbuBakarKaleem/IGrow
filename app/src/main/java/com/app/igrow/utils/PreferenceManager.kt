package com.app.igrow.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.app.igrow.IGrowApp
import com.app.igrow.utils.Constants.DEFAULT_LANGUAGE
import com.app.igrow.utils.Constants.KEY_COUNTRY
import com.app.igrow.utils.Constants.KEY_LANGUAGE


class PreferenceManager(context: Context?) {

    init {
        pref = if (context != null) {
            PreferenceManager.getDefaultSharedPreferences(context)
        } else {
            IGrowApp.getInstance().let { PreferenceManager.getDefaultSharedPreferences(it) }
        }
    }

    companion object {
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
        return pref!!.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE)
    }

    fun setLanguage(language: String?) {
        pref!!.edit().putString(KEY_LANGUAGE, language).apply()
    }

    fun getCountry(): String? {
        return pref!!.getString(KEY_COUNTRY, "")
    }

    fun setCountry(countryName: String?) {
        pref!!.edit().putString(KEY_COUNTRY, countryName).apply()
    }
}