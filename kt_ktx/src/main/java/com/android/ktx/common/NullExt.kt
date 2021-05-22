package com.android.ktx.common


@JvmOverloads
inline fun <reified T> T?.isNull(notNull: () -> Unit = {}, nullAction: () -> Unit = {}) {
    when (this) {
        null -> nullAction.invoke()
        is List<*> -> if (this.isEmpty()) nullAction.invoke() else notNull.invoke()
        else -> notNull.invoke()
    }
}

/**
*
*只是上面的取反
**/
@JvmOverloads
inline fun <reified T> T?.notNull(nullAction: () -> Unit = {}, notNull: () -> Unit = {}) {
    this.isNull(notNull, nullAction)
}

inline fun <reified T> T?.isNull(): Boolean {
    return when (this) {
        null -> true
        is List<*> -> this.isEmpty()
        else -> false
    }
}
/**
*
*只是上面的取反
**/
inline fun <reified T> T?.notNull(): Boolean {
    return !this.isNull()
}
