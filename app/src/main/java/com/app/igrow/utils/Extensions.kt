package com.app.igrow.utils

import android.view.View
import android.widget.EditText

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
fun View.clear() {
    var editText=this as EditText
    editText.text.clear()
}
fun View.disable() {
    var editText=this as EditText
    editText.isEnabled=false
}
