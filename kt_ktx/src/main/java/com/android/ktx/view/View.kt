package com.android.ktx.view

import android.os.Build
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi


/**
 * View 相关的Kotlin扩展方法
 * <p>
 */


/**
 * 隐藏 View
 */
fun View.hide(isTrue: Boolean = true) {
    visibility = if (isTrue) GONE else VISIBLE
}


/**
 * 显示/隐藏 View
 * @param isTrue {@code true } 显示<br>{@code false} 隐藏
 */
fun View.show(isTrue: Boolean = true) {
    visibility = if (isTrue) VISIBLE else GONE
}

/**
 * invisible/显示 View
 * @param `true` INVISIBLE, `false` VISIBLE
 */
fun View.invisible(isTrue: Boolean = true) {
    visibility = if (isTrue) INVISIBLE else VISIBLE
}


/**
 * 判断View是不是可见
 *
 * @return `true` 可见([View.getVisibility] == [View.VISIBLE])
 */
fun View.isVisible(): Boolean {
    return visibility == VISIBLE
}

/**
 * 判断View是不是可见
 *
 * @return `true` 可见([View.getVisibility] == [View.VISIBLE])
 */
fun View.isShow(): Boolean {
    return isVisible()
}

/**
 * 判断View是不是可见
 *
 * @return `true` 可见([View.getVisibility] == [View.INVISIBLE])
 */
fun View.isInvisible(): Boolean {
    return visibility == INVISIBLE
}

/**
 * 判断View是不是可见
 *
 * @return `true` 可见([View.getVisibility] == [View.GONE])
 */
fun View.isGone(): Boolean {
    return visibility == GONE
}


/**
 * 判断View是不是可见
 *
 * @return `true` 可见([View.getVisibility] == [View.GONE])
 */
fun View.isHide(): Boolean {
    return isGone()
}


/**
 * [View]点击事件，点击[doActionAfterTimes]次后执行[action]，如果[doActionAfterTimes]==1,
 * [millisecondInterval]是防止重复点击的间隔；如果[doActionAfterTimes]>1，相当于双击，多次点击，
 * 以[millisecondInterval]内点击记录点击次数，点击次数到[doActionAfterTimes]，触发[action].
 *
 * [doActionAfterTimes]默认为1 [millisecondInterval]默认500ms.
 * @since 3.0.0
 */
fun View.onClick(
    @IntRange(from = 1, to = Long.MAX_VALUE) doActionAfterTimes: Int = 1,
    @IntRange(from = 1, to = Long.MAX_VALUE) millisecondInterval: Int = 500,
    action: () -> Unit
) {
    Views.onClick(this, doActionAfterTimes, millisecondInterval, action)
}


/**
 * 设置防止重复点击事件
 * @param views 需要设置点击事件的view
 * @param interval 时间间隔 默认0.5秒
 * @param onClick 点击触发的方法
 */
fun setOnclickNoRepeat(vararg views: View?, interval: Long = 500, onClick: (View) -> Unit) {
    views.forEach {
        it?.clickNoRepeat(interval = interval) { view ->
            onClick.invoke(view)
        }
    }
}

/**
 * 防止重复点击事件 默认0.5秒内不可重复点击
 * @param interval 时间间隔 默认0.5秒
 * @param action 执行方法
 */
var lastClickTime = 0L
var lastViewId = Int.MAX_VALUE
fun View.clickNoRepeat(interval: Long = 500, action: (view: View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime < interval) && it.getViewId()== lastViewId) {
            return@setOnClickListener
        }
        lastClickTime = currentTime
        lastViewId = it.id
        action.invoke(it)
    }
}

/**
 * 设置点击事件
 * @param views 需要设置点击事件的view
 * @param onClick 点击触发的方法
 */
fun setOnclick(vararg views: View?, onClick: (View) -> Unit) {
    views.forEach {
        it?.setOnClickListener { view ->
            onClick.invoke(view)
        }
    }
}

/**
 * 获取[View] id，如果没有id：如果SDK>17, 使用[View.generateViewId]；否则使用[View.hashCode]
 */
fun View.getViewId(): Int {
    var id = id
    if (id == NO_ID) {
        id = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            generateViewId()
        } else {
            this.hashCode()
        }
    }

    return id
}


/**
 *
 *设置view的MarginRight
 **/
fun View?.setViewMarginRight(marginRight: Int) {
    this?.apply {
        runCatching {
            layoutParams as? ViewGroup.MarginLayoutParams
                ?: ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                    ViewGroup.MarginLayoutParams.MATCH_PARENT
                )
        }.onSuccess {
            it.setMargins(it.leftMargin, it.topMargin, marginRight, it.bottomMargin)
            layoutParams = it
        }
    }
}


/**
 *
 *设置view的MarginLeft
 **/
fun View?.setViewMarginLeft(marginLeft: Int) {
    this?.apply {
        runCatching {
            layoutParams as? ViewGroup.MarginLayoutParams
                ?: ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                    ViewGroup.MarginLayoutParams.MATCH_PARENT
                )
        }.onSuccess {
            it.setMargins(marginLeft, it.topMargin, it.rightMargin, it.bottomMargin)
            layoutParams = it
        }
    }
}


/**
 *
 *设置view的marginTop
 **/
fun View?.setViewMarginTop(marginTop: Int) {
    this?.apply {
        runCatching {
            layoutParams as? ViewGroup.MarginLayoutParams
                ?: ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                    ViewGroup.MarginLayoutParams.MATCH_PARENT
                )
        }.onSuccess {
            it.setMargins(it.leftMargin, marginTop, it.rightMargin, it.bottomMargin)
            layoutParams = it
        }
    }
}

/**
 *
 *设置view的marginBottom
 **/
fun View?.setViewMarginBottom(marginBottom: Int) {
    this?.apply {
        runCatching {
            layoutParams as? ViewGroup.MarginLayoutParams
                ?: ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                    ViewGroup.MarginLayoutParams.MATCH_PARENT
                )
        }.onSuccess {
            it.setMargins(it.leftMargin, it.topMargin, it.rightMargin, marginBottom)
            layoutParams = it
        }
    }
}

/**
 *
 *设置view的MarginHorizontal
 **/
fun View?.setViewMarginHorizontal(left: Int, right: Int) {
    this?.apply {
        runCatching {
            layoutParams as? ViewGroup.MarginLayoutParams
                ?: ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                    ViewGroup.MarginLayoutParams.MATCH_PARENT
                )
        }.onSuccess {
            it.setMargins(left, it.topMargin, right, it.bottomMargin)
            layoutParams = it
        }
    }
}

fun View?.setViewMarginHorizontal(marginHorizontal: Int) {
    setViewMarginHorizontal(marginHorizontal, marginHorizontal)
}

/**
 *
 *设置view的MarginHorizontal
 **/
fun View?.setViewMarginVertical(top: Int, bottom: Int) {
    this?.apply {
        runCatching {
            layoutParams as? ViewGroup.MarginLayoutParams
                ?: ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                    ViewGroup.MarginLayoutParams.MATCH_PARENT
                )
        }.onSuccess {
            it.setMargins(it.leftMargin, top, it.rightMargin, bottom)
            layoutParams = it
        }
    }
}

fun View?.setViewMarginVertical(marginVertical: Int) {
    setViewMarginVertical(marginVertical, marginVertical)
}


/**
 *
 *设置view的Margin
 **/
fun View?.setViewMargin(marginLeft: Int, marginTop: Int, marginRight: Int, marginBottom: Int) {
    this?.apply {
        runCatching {
            layoutParams as? ViewGroup.MarginLayoutParams
                ?: ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                    ViewGroup.MarginLayoutParams.MATCH_PARENT
                )
        }.onSuccess {
            it.setMargins(marginLeft, marginTop, marginRight, marginBottom)
            layoutParams = it
        }
    }
}

fun View?.getMarginTop(): Int {
    return if (this == null) -1 else {
        val layoutParam = layoutParams as? ViewGroup.MarginLayoutParams
        if (layoutParams == null) -1 else layoutParam!!.topMargin
    }
}

fun View?.getMarginLeft(): Int {
    return if (this == null) -1 else {
        val layoutParam = layoutParams as? ViewGroup.MarginLayoutParams
        if (layoutParams == null) -1 else layoutParam!!.leftMargin
    }
}

fun View?.getMarginRight(): Int {
    return if (this == null) -1 else {
        val layoutParam = layoutParams as? ViewGroup.MarginLayoutParams
        if (layoutParams == null) -1 else layoutParam!!.rightMargin
    }
}

fun setViewWidthHeight(view: View?, width: Int, height: Int) {
    if (view != null) {
        var layoutParams = view.layoutParams
        if (layoutParams == null) {
            layoutParams = ViewGroup.MarginLayoutParams(-2, -2)
        }
        (layoutParams as ViewGroup.LayoutParams).width = width
        layoutParams.height = height
        view.layoutParams = layoutParams
    }
}

fun View?.setViewWidth(width: Int) {
    this?.let {
        var layoutParams = it.layoutParams
        if (layoutParams == null) {
            layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        (layoutParams as ViewGroup.LayoutParams).width = width
        it.layoutParams = layoutParams
    }
}

fun View?.setViewHeight(height: Int) {
    this?.let {
        var layoutParams = it.layoutParams
        if (layoutParams == null) {
            layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        (layoutParams as ViewGroup.LayoutParams).height = height
        it.layoutParams = layoutParams
    }
}

fun View?.setViewWidthMarchParent() {

    setViewWidth(ViewGroup.LayoutParams.MATCH_PARENT)

}

fun View?.setViewHeightMarchParent(view: View?) {

        setViewHeight(ViewGroup.LayoutParams.MATCH_PARENT)
}


fun View?.getMarginBottom(): Int {
    return if (this == null) -1 else {
        val layoutParam = layoutParams as? ViewGroup.MarginLayoutParams
        if (layoutParams == null) -1 else layoutParam!!.bottomMargin
    }
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
fun View?.visibleListener(onViewVisibleListener: OnViewVisibleListener) {
    this?.let {
        viewTreeObserver.addOnGlobalLayoutListener {
            onViewVisibleListener.visible(it)
        }
    }
}

interface OnViewVisibleListener {
    fun visible(var1: View)
}



