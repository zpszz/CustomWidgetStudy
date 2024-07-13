package com.jpc.chapter12.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.displayhash.DisplayHashResultCallback
import java.util.concurrent.Executor
import kotlin.math.max

class MyLinLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null): ViewGroup(context, attributeSet){
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var top: Int = 0
        val count = childCount
        for(i in 0 until count){
            val child = getChildAt(i)
            val layoutParams = child.layoutParams as MarginLayoutParams
            val height = child.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin
            val width = child.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
            // 四个参数依次为：控件的 左边、上边、右边、下边
            // 定义各个子控件的位置
            child.layout(0, top, width, top+height)
            top += height
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)
        val measureWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val measureHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        var width: Int = 0
        var height: Int = 0
        val count = childCount
        for (i in 0 until count){
            // 测量子控件
            val childAt = getChildAt(i)
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec)
            // 获得布局参数
            val layoutParams = childAt.layoutParams as MarginLayoutParams
            // 获得子控件的高度和宽度，要考虑外边距
            val childHeight = childAt.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin
            val childWidth = childAt.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
            // 得到最大宽度，并且累加高度
            height += childHeight
            width = max(childWidth, width)
        }
        // 如果控件中对应属性width指定为具体值即EXACTLY模式，就使用定义的值measureWidth，否则使用测量值width
        setMeasuredDimension(if(measureWidthMode == MeasureSpec.EXACTLY) measureWidth else width,
            if(measureHeightMode == MeasureSpec.EXACTLY) measureHeight else height)
    }


    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }
}