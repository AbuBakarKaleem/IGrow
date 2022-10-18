package com.app.igrow.utils

import android.os.Build
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.model.sheets.Products
import com.google.gson.GsonBuilder
import java.util.*


object Utils {

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

    fun parseHashMapToObject(map: HashMap<String,String>, cls: Class<*>?): Any? {
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()
        val jsonString = gson.toJson(map)
        return gson.fromJson<Any>(jsonString, cls)
    }
}