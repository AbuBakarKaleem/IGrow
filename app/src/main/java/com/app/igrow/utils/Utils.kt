package com.app.igrow.utils

import android.os.Build
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.model.sheets.Products
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

    fun sheetListToMap(dataList: ArrayList<Any>): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        try {
            dataList.forEach {
                when(it){
                    is Diagnostic->{
                        map[it.id] = it
                    }
                    is Distributors->{
                        map[it.id] = it
                    }
                    is Dealers ->{
                        map[it.id] = it
                    }
                    is Products -> {
                        map[it.id] = it
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }
}