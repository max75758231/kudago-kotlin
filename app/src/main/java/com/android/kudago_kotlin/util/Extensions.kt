package com.android.kudago_kotlin.util

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun TextView.setTextOrHideIfNull(text: String?) {
    text?.let {
        this.setVisible(true)
        this.text = it
    } ?: this.setVisible(false)
}

fun Context.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()