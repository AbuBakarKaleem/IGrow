package com.app.igrow.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.sqlite.db.SimpleSQLiteQuery
import com.google.gson.GsonBuilder
import java.util.*


object Utils {

    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        return result
    }


    fun getSystemLanguage(): String {
        var language = Locale.getDefault().toString()
        language = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Locale.getDefault().toLanguageTag()
        } else {
            language.replace("_", "-")
        }
        return language
    }

    fun getFileMimeType(): Array<String> {
        return arrayOf(
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",  // .xls & .xlsx
        )
    }

    fun getLocalizeColumnName(actualName: String): String {
        if (Utils.getSystemLanguage().split("-")[0] == "fr") {
            return actualName + "_fr"
        }
        return actualName
    }

    fun isLocaleFrench(): Boolean {
        return getSystemLanguage() == "fr"
    }

    fun parseHashMapToObject(map: HashMap<String, String>, cls: Class<*>?): Any? {
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()
        val jsonString = gson.toJson(map)
        return gson.fromJson<Any>(jsonString, cls)
    }

    fun getColumnDataCustomQuery(sheetName: String, columnName: String): SimpleSQLiteQuery {
        try {
            if (sheetName.isNotEmpty() && columnName.isNotEmpty()) {
                val customQuery: String = "SELECT DISTINCT $columnName FROM $sheetName"
                return SimpleSQLiteQuery(customQuery)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return SimpleSQLiteQuery("")
    }

}