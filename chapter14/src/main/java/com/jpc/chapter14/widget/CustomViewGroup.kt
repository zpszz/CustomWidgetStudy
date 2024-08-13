package com.jpc.chapter14.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.max

/**
 * 继承自ViewGroup类
 */
class CustomViewGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttr) {

    /**
     * 重写onMeasure函数，不仅需要适配wrap_content模式，还需要显式地调用measureChildren函数，以测量所有子控件。
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 测量子控件
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        var height = 0
        var maxWidth = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childWidth = child.measuredWidth
            // 宽度为wrap_content，就取子控件中的最大宽度
            maxWidth = max(maxWidth, childWidth)
            val childHeight = child.measuredHeight
            // 高度为wrap_content，就取所有子控件的高度总和
            height += childHeight
        }

        val widthSpec = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpec = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthSpec == MeasureSpec.AT_MOST && heightSpec == MeasureSpec.AT_MOST){
            setMeasuredDimension(maxWidth, height)
        }else if (widthSpec == MeasureSpec.AT_MOST){
            setMeasuredDimension(maxWidth, heightSize)
        }else if (heightSpec == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize, height)
        }else{
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        }
    }

    /**
     * 重写onLayout函数，根据想要的效果，布局每个子控件
     * 实现纵向排列布局
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var totalTop = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            // 确定每一个子控件的位置
            child.layout(0, totalTop, childWidth, totalTop + childHeight)
            totalTop += childHeight
        }
    }

}