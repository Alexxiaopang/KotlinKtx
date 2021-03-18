package com.android.ktx.edittext

import com.android.ktx.edittext.EmojiFilter

/**
 * 密码筛选器.
 */
class PasswordFilter: EmojiFilter(){
    init {
        BASE_REGEX = "^[0-9a-zA-Z!#$%&'()*+,\\-.:;=?@\\[\\\\\\]^_`{|}~]+$"
    }
}