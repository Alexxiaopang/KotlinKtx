package com.android.ktx.spanner

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.*
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.collection.ArrayMap
import androidx.core.content.ContextCompat
import com.android.ktx.provider.ContextProvider

import java.util.*
import java.util.regex.Pattern

/**
 * 简化富文本上色，点击，加粗等功能的SpannableString.
 */
class Spanner private constructor(private val context: Context, text: CharSequence) :
    SpannableString(text) {
    private val rangeList = ArrayList<Range>()
    private val tagsMap = ArrayMap<Range, Any?>()
    private var textColor: Int = 0
    private var pressedTextColor: Int = 0
    private var pressedBackgroundColor: Int = 0
    private var pressedBackgroundRadius: Int = 0

    fun first(target: String): Spanner {
        if (TextUtils.isEmpty(target)) {
            return this
        }

        rangeList.clear()
        val index = toString().indexOf(target)
        val range = Range.create(index, index + target.length)
        rangeList.add(range)
        return this
    }

    fun last(target: String): Spanner {
        if (TextUtils.isEmpty(target)) {
            return this
        }

        rangeList.clear()
        val index = toString().lastIndexOf(target)
        val range = Range.create(index, index + target.length)
        rangeList.add(range)
        return this
    }

    fun all(vararg targets: String): Spanner {
        rangeList.clear()

        for (target in targets) {
            if (TextUtils.isEmpty(target)) {
                continue
            }
            val indexes = SpannerUtils.indexesOf(toString(), target)
            for (index in indexes) {
                val range = Range.create(index, index + target.length)
                rangeList.add(range)
            }
        }
        return this
    }

    fun all(): Spanner {
        rangeList.clear()
        val range = Range.create(0, toString().length)
        rangeList.add(range)
        return this
    }

    fun allStartWith(vararg prefixs: String): Spanner {
        rangeList.clear()
        for (prefix in prefixs) {
            if (TextUtils.isEmpty(prefix)) {
                continue
            }
            val ranges = SpannerUtils.ranges(toString(), Pattern.quote(prefix) + "\\w+")
            rangeList.addAll(ranges)
        }
        return this
    }

    fun range(from: Int, to: Int): Spanner {
        rangeList.clear()
        val range = Range.create(from, to + 1)
        rangeList.add(range)
        return this
    }

    fun ranges(ranges: List<Range>): Spanner {
        rangeList.clear()
        rangeList.addAll(ranges)
        return this
    }

    fun between(startText: String, endText: String): Spanner {
        rangeList.clear()
        val startIndex = toString().indexOf(startText) + startText.length + 1
        val endIndex = toString().lastIndexOf(endText) - 1
        val range = Range.create(startIndex, endIndex)
        rangeList.add(range)
        return this
    }

    fun size(dp: Int): Spanner {
        for (range in rangeList) {
            setSpan(AbsoluteSizeSpan(dp, true), range.from, range.to, SPAN_MODE)
        }
        return this
    }

    fun scaleSize(proportion: Int): Spanner {
        for (range in rangeList) {
            setSpan(RelativeSizeSpan(proportion.toFloat()), range.from, range.to, SPAN_MODE)
        }
        return this
    }

    fun bold(): Spanner {
        for (range in rangeList) {
            setSpan(StyleSpan(Typeface.BOLD), range.from, range.to, SPAN_MODE)
        }
        return this
    }

    fun italic(): Spanner {
        for (range in rangeList) {
            setSpan(StyleSpan(Typeface.ITALIC), range.from, range.to, SPAN_MODE)
        }
        return this
    }

    fun normal(): Spanner {
        for (range in rangeList) {
            setSpan(StyleSpan(Typeface.NORMAL), range.from, range.to, SPAN_MODE)
        }
        return this
    }

    fun boldItalic(): Spanner {
        for (range in rangeList) {
            setSpan(StyleSpan(Typeface.BOLD_ITALIC), range.from, range.to, SPAN_MODE)
        }
        return this
    }

    fun font(font: String): Spanner {
        for (range in rangeList) {
            setSpan(TypefaceSpan(font), range.from, range.to, SPAN_MODE)
        }
        return this
    }

    fun strikethrough(): Spanner {
        for (range in rangeList) {
            setSpan(StrikethroughSpan(), range.from, range.to, SPAN_MODE)
        }
        return this
    }

    fun underline(): Spanner {
        for (range in rangeList) {
            setSpan(UnderlineSpan(), range.from, range.to, SPAN_MODE)
        }
        return this
    }

    /**
     * 设置选中字体背景色.注意：需要同时设置 [.textColor]
     */
    @JvmOverloads
    fun backgroundColor(
        @ColorInt color: Int, @ColorInt pressedBackgroundColor: Int = color,
        radiusDp: Int = 0
    ): Spanner {
        this.pressedBackgroundColor = pressedBackgroundColor
        this.pressedBackgroundRadius = SpannerUtils.dp2px(context, radiusDp)

        val radiusPx = SpannerUtils.dp2px(context, radiusDp)
        for (range in rangeList) {
            setSpan(
                RoundedBackgroundSpan(textColor, color, radiusPx), range.from, range.to,
                SPAN_MODE
            )
        }
        return this
    }

    @JvmOverloads
    fun backgroundColorRes(
        @ColorRes colorRes: Int, @ColorRes pressedBackgroundColorRes: Int = colorRes,
        radiusDp: Int = 0
    ): Spanner {
        val color = ContextCompat.getColor(context, colorRes)
        val pressedBackgroundColor = ContextCompat.getColor(context, pressedBackgroundColorRes)
        return backgroundColor(color, pressedBackgroundColor, radiusDp)
    }

    @JvmOverloads
    fun textColorRes(
        @ColorRes colorRes: Int,
        @ColorRes pressedTextColorRes: Int = colorRes
    ): Spanner {
        return textColor(
            ContextCompat.getColor(context, colorRes),
            ContextCompat.getColor(context, pressedTextColorRes)
        )
    }

    @JvmOverloads
    fun textColor(@ColorInt color: Int, @ColorInt pressedTextColor: Int = color): Spanner {
        textColor = color
        this.pressedTextColor = pressedTextColor
        for (range in rangeList) {
            setSpan(ForegroundColorSpan(textColor), range.from, range.to, SPAN_MODE)
        }
        return this
    }


    fun subscript(): Spanner {
        for (range in rangeList) {
            setSpan(SubscriptSpan(), range.from, range.to, SPAN_MODE)
        }
        return this
    }

    fun superscript(): Spanner {
        for (range in rangeList) {
            setSpan(SuperscriptSpan(), range.from, range.to, SPAN_MODE)
        }
        return this
    }

    fun url(url: String): Spanner {
        if (TextUtils.isEmpty(url)) {
            return this
        }

        for (range in rangeList) {
            val span = CustomClickableSpan(
                subSequence(range.from, range.to),
                url,
                range,
                object : OnTextClickListener {
                    override fun onClicked(text: CharSequence, range: Range, tag: Any?) {
                        SpannerUtils.openURL(context, url)
                    }
                })
            setSpan(span, range.from, range.to, SPAN_MODE)
        }
        return this
    }

    fun pressedBackgroundColor(@ColorInt color: Int): Spanner {
        return pressedBackgroundColor(color, 0)
    }

    fun pressedBackgroundColor(@ColorInt color: Int, radiusDp: Int): Spanner {
        this.pressedBackgroundColor = color
        this.pressedBackgroundRadius = SpannerUtils.dp2px(context, radiusDp)
        return this
    }

    @JvmOverloads
    fun pressedBackgroundRes(@ColorRes colorRes: Int, radiusDp: Int = 0): Spanner {
        this.pressedBackgroundColor = ContextCompat.getColor(context, colorRes)
        this.pressedBackgroundRadius = SpannerUtils.dp2px(context, radiusDp)
        return this
    }

    fun onClick(
        textView: TextView,
        onTextClickListener: OnTextClickListener
    ): Spanner {
        for (range in rangeList) {
            val span = CustomClickableSpan(
                subSequence(range.from, range.to),
                tagsMap[range],
                range,
                onTextClickListener
            )
            setSpan(span, range.from, range.to, SPAN_MODE)
        }
        linkify(textView)
        return this
    }

    fun onClick(
        textView: TextView,
        onTextClickListener: OnTextClickListener, dsAction: (TextPaint) -> Unit
    ): Spanner {
        for (range in rangeList) {
            val span = CustomClickableSpan(
                subSequence(range.from, range.to),
                tagsMap[range],
                range,
                onTextClickListener, dsAction
            )
            setSpan(span, range.from, range.to, SPAN_MODE)
        }
        linkify(textView)
        return this
    }


    fun onLongClick(
        textView: TextView,
        onTextLongClickListener: OnTextLongClickListener
    ): Spanner {
        for (range in rangeList) {
            val span = CustomClickableSpan(
                subSequence(range.from, range.to),
                tagsMap[range],
                range,
                onTextLongClickListener
            )
            setSpan(span, range.from, range.to, SPAN_MODE)
        }
        linkify(textView)
        return this
    }

    fun tag(tag: Any): Spanner {
        val lastRange = rangeList[rangeList.size - 1]
        tagsMap[lastRange] = tag
        return this
    }

    fun tags(vararg tags: Any): Spanner {
        return tags(Arrays.asList(*tags))
    }

    fun tags(tags: List<Any>): Spanner {
        var i = 0
        for (tag in tags) {
            if (tag != null) {
                tagsMap[rangeList[i++]] = tag
            }
        }
        return this
    }

    private fun linkify(textView: TextView) {
        textView.highlightColor = Color.TRANSPARENT
        textView.movementMethod = LinkTouchMovementMethod(
            pressedTextColor, pressedBackgroundColor,
            pressedBackgroundRadius
        )
    }

    companion object {

        private val SPAN_MODE = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

        @JvmStatic
        fun from(text: CharSequence): Spanner {
            checkNotNull(text)
            return Spanner(ContextProvider.getContext(), text)
        }
    }

}
