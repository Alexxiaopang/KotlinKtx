package com.android.ktx.edittext

import android.text.InputFilter
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.IntRange
import com.android.ktx.view.Views
import com.android.ktx.context.getInputMethodManager



/**
 * @author ['s GitHub](https://github.com/Ayvytr)
 * @since 2.4.0
 */

/**
 * 设置 [EditText] 文本，并且把光标定位到末尾
 */
fun EditText.setText2(text: CharSequence) {
    setText(text)
    setSelection(getText().length)
}

/**
 * 设置 [EditText] 文本，并且全选文本
 */
fun EditText.setTextWithSelection(text: CharSequence) {
    setText(text)
    setSelection(0, getText().length)
}

/**
 * 增加[EditText] [filter]
 */
fun EditText.addFilters(filter: InputFilter) {
    addFilters(listOf(filter))
}

/**
 * 设置 [EditText] filters, 先获取原有filters，和[filters]一并设置到[EditText]
 */
fun EditText.addFilters(filters: List<InputFilter>) {
    val list = filters.toMutableList()
    list.addAll(getFilters())
    setFilters(list.toTypedArray())
}

/**
 * 显示软键盘，[isShow]:显示，在[android.app.Activity.onCreate]中使用也可以弹出键盘.
 */
fun EditText.showInputMethod(isShow: Boolean = true) {
    if (isShow) {
        postDelayed({
                        isFocusable = true
                        requestFocus()
                        val imm = context.getInputMethodManager()
                        imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
                    }, 100)
    } else {
        hideInputMethod()
    }
}

/**
 * 隐藏软键盘
 */
fun EditText.hideInputMethod() {
    val imm = context.getInputMethodManager()
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * [EditText]文本变化监听器，文本变化后，以[timeout]为时间间隔触发[action]，[ignoreEmpty]=true: 输入文本为
 * 空时，不触发[action].
 * [timeout]默认300ms，默认[ignoreEmpty]=true，忽略空字符串.
 * @since 3.0.0
 * 灵感：RxBinding TextView.textChanges。这里直接以[EditText]实现，[android.widget.TextView]没有必要.
 */
fun EditText.textChange(@IntRange(from = 1L, to = Long.MAX_VALUE) timeout: Int = 300,
                        ignoreEmpty: Boolean = true,
                        action: (text: String) -> Unit) {
    Views.textChange(this, timeout, ignoreEmpty, action)
}