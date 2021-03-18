package com.puffer.live.extention

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import java.lang.reflect.Field

//设置viewpage灵敏度
@JvmOverloads
fun ViewPager2.touchSlopField(touchSlopDefault: Int = 5) {

    try {
        val recyclerViewField: Field = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true;
        val recyclerView = recyclerViewField.get(this) as RecyclerView
        val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
        touchSlopField.isAccessible = true
        val touchSlop: Int = touchSlopField.get(recyclerView) as Int
        touchSlopField.set(recyclerView, touchSlop * touchSlopDefault)
    } catch (ignore: Exception) {

        ignore.printStackTrace()

    }
}