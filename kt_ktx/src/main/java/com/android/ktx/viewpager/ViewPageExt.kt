package com.puffer.live.extention

import androidx.viewpager.widget.ViewPager
import java.lang.reflect.Field

/**
 *
 * @ProjectName:    login_branch
 * @Package:        com.puffer.live.extention
 * @ClassName:      ViewPageExt
 * @Description:     java类作用描述
 * @Author:         Alex
 * @CreateDate:     2020/12/7 9:34
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/12/7 9:34
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
//设置默认的选中
fun ViewPager.setDefaultItem(position: Int) {
    try {
        val c = Class.forName("androidx.viewpager.widget.ViewPager")
        val field: Field = c.getDeclaredField("mCurItem")
        field.isAccessible = true
        field.setInt(this, position)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    adapter!!.notifyDataSetChanged()
    this.currentItem = position
}
