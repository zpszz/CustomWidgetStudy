package com.jpc.chapter16.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

/**
 * 单手指移动图片
 */
class SingleDragImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr) {
    private var mLeft = 0
    private var mTop = 0
    private var mStartX = 0f
    private var mStartY = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        performClick()
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                // 记录手指按下的位置
                mStartX = event.x
                mStartY = event.y
                mLeft = left
                mTop = top
            }
            MotionEvent.ACTION_MOVE -> {
                mLeft = (mLeft + event.x - mStartX).toInt()
                mTop = (mTop + event.y - mStartY).toInt()
                layout(mLeft, mTop, mLeft + width, mTop +height)
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}