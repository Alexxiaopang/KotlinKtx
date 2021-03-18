package com.android.ktx.recyclerview

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 *
 * @ProjectName:    JetPackViewModel
 * @Package:        com.android.me.alex.extension
 * @ClassName:      RecyclerViewExt
 * @Description:     java类作用描述
 * @Author:         Alex
 * @CreateDate:     2020/7/30 13:40
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/7/30 13:40
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
fun RecyclerView.vertical(spanCount: Int = 0, isStaggered: Boolean = false): RecyclerView {
    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    if (spanCount != 0) {
        layoutManager = GridLayoutManager(context, spanCount)
    }
    if (isStaggered) {
        layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
    }
    return this
}

fun RecyclerView.horizontal(spanCount: Int = 0, isStaggered: Boolean = false): RecyclerView {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    if (spanCount != 0) {
        layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.HORIZONTAL, false)
    }
    if (isStaggered) {
        layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL)
    }
    return this
}
fun RecyclerView.smoothScrollToEnd(){
    if(adapter!=null && adapter!!.itemCount>0) smoothScrollToPosition(adapter!!.itemCount)
}

fun RecyclerView.scrollToEnd(){
    if(adapter!=null && adapter!!.itemCount>0) scrollToPosition(adapter!!.itemCount)
}



/**
 * 函数配置分割线
 * 具体配置参数查看[DefaultDecoration]
 */
fun RecyclerView.divider(
        block: DefaultDecoration.() -> Unit
): RecyclerView {
    val itemDecoration = DefaultDecoration(context).apply(block)
    addItemDecoration(itemDecoration)
    return this
}

/**
 * 指定Drawable资源为分割线, 分割线的间距和宽度应在资源文件中配置
 * @param drawable 描述分割线的drawable
 * @param orientation 分割线方向, 仅[androidx.recyclerview.widget.GridLayoutManager]需要使用此参数, 其他LayoutManager都是根据其方向自动推断
 */
fun RecyclerView.divider(
        @DrawableRes drawable: Int,
        orientation: DividerOrientation = DividerOrientation.HORIZONTAL
): RecyclerView {
    return divider {
        setDrawable(drawable)
        this.orientation = orientation
    }
}


/**
 * 滚动置顶，只支持线性布局
 */
fun RecyclerView.scrollTop(position: Int){
    smoothScrollToPosition(position)
//    if(layoutManager is LinearLayoutManager){
//        (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
//
//    }
}