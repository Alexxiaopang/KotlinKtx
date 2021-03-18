package com.android.ktx.edittext

/**
 * Email筛选器.
 */
open class EmailFilter : EmojiFilter() {
    init {
        BASE_REGEX = "^[0-9a-zA-Z@._]+$"
    }
}

