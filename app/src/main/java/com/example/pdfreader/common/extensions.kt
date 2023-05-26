package com.example.pdfreader.common

import android.view.View

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun View?.visible(isVisible: Boolean) {
    this?.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View?.hide() {
    this?.visibility = View.GONE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}