package com.jpc.chapter12.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.max

class FlowLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null): ViewGroup(context, attributeSet){
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        var lineWidth = 0 // 累加当前行的行宽
        var lineHeight = 0 // 当前行的行高
        var top = 0 // 当前控件的top坐标
        var left = 0 // 当前控件的left坐标
        for (i in 0 until count){
            val child = getChildAt(i)
            val lp = child.layoutParams as MarginLayoutParams
            // child的大小要算上他的外边距
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            if(childWidth + lineWidth > measuredWidth){
                // 需要换行
                top += lineHeight
                left = 0
                lineHeight = childHeight
                lineWidth = childWidth
            }else{
                // 不需要换行
                lineHeight = max(lineHeight, childHeight)
                lineWidth += childWidth
            }
            // 计算childView的left，top，right， bottom
            val childLeft = left + lp.leftMargin
            val childTop = top + lp.topMargin
            val childRight = childLeft + child.measuredWidth
            val childBottom = childTop + child.measuredHeight
            // 调用child的layout函数进行布局
            child.layout(childLeft, childTop, childRight, childBottom)
            // 将left设置为下一个子控件的起始点
            left += childWidth
        }
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)
        val measureWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val measureHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        var lineWidth = 0 // 每一行的宽度
        var lineHeight = 0 // 每一行的高度
        var height = 0 // 整个FlowLayout占据的高度
        var width = 0 // 整个FlowLayout占据的宽度
        val count = childCount
        for (i in 0 until count){
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            if(lineWidth + childWidth > measureWidth){
                // 当前行已有的宽度+当前子控件的宽度 大于 FlowLayout的宽度时，需要换行
                width = max(lineWidth, width)
                height += lineHeight
                // 将此控件放在下一行
                lineWidth = childWidth
                lineHeight = childHeight
            }else{
                // 当前行放得下
                lineHeight = max(lineHeight, childHeight)
                lineWidth += childWidth
            }
            // 因为最后一行是不会超出width，所以单独处理
            if (i == count-1){
                height += lineHeight
                width = max(width, lineWidth)
            }
        }
        // 当属性是EXACTLY时，控件的高度是确定的，当属性是wrap_content时，是通过内部控件的大小来确定父控件的大小，所以就需要进行计算得出
        setMeasuredDimension(if(measureWidthMode == MeasureSpec.EXACTLY) measureWidth else width,
            if(measureHeightMode == MeasureSpec.EXACTLY) measureHeight else height)
    }
}