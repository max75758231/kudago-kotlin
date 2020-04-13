package com.android.kudago_kotlin.util

import android.content.Context
import android.os.Build
import android.text.Html
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

fun TextView.setLinkableHtmlText(text: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    } else {
        this.text = Html.fromHtml(text)
    }
}

fun Context.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun <T> List<T>.isLast(index: Int) = size == index + 1