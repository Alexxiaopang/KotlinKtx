package com.android.ktx.view

import android.graphics.Paint
import android.widget.TextView


fun TextView?.textBold() {
    setTextBold(true)
}

fun TextView?.setTextBold(isBold: Boolean) {
    this?.paint?.isFakeBoldText = isBold
}

fun TextView?.textUnderline() {
    this?.paint?.flags = Paint.UNDERLINE_TEXT_FLAG
    this?.paint?.isAntiAlias = true
}