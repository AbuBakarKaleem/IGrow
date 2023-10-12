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
    (this as EditText).text.clear()
}
fun View.disable() {
    (this as EditText).isEnabled = false
}
