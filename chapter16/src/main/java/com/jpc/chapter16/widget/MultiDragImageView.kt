package com.jpc.chapter16.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

/**
 * 多点触控下，移动图片
 */
class MultiDragImageView  @JvmOverloads constructor(
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
        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                // 记录手指按下的位置
                mStartX = event.x
                mStartY = event.y
                mLeft = left
                mTop = top
            }
            MotionEvent.ACTION_MOVE -> {
                // 只有第一根手指可以
                val index = event.findPointerIndex(0) // pointerId为0代表第一根手指
                /**
                 * 第1根手指的PointerId是0，这里根据PointerId获取了对应的PointerIndex，当获取不到PointerIndex时，
                 * 就表示第1根手指已经不在屏幕上了，这时会返回-1。当返回-1时，我们返回false，表示已经不需要捕捉消息了。
                 */
                if (index == -1)return false
                mLeft = (mLeft + event.getX(index) - mStartX).toInt()
                mTop = (mTop + event.getY(index) - mStartY).toInt()
                layout(mLeft, mTop, mLeft + width, mTop +height)
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}