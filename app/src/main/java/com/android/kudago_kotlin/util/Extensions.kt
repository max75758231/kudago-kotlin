package com.android.kudago_kotlin.util

import android.view.View
import android.widget.TextView

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun TextView.setTextOrHideIfNull(text: String?) {
    text?.let {
        this.setVisible(true)
        this.text = it
    } ?: this.setVisible(false)
}