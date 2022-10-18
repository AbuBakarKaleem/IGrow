package com.app.igrow.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.igrow.utils.LocaleHelper
import com.app.igrow.utils.PreferenceManager


open class BaseActivity : AppCompatActivity() {
    var context: BaseActivity? = null
    var mySharePreference: PreferenceManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        mySharePreference = PreferenceManager(this)
        context?.let { LocaleHelper.setLocale(it, mySharePreference!!.getLanguage()) }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

}
