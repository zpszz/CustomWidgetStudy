package com.jpc.chapter16.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * 自定义可缩放的文本视图
 */
class TouchScaleTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
): AppCompatTextView(context, attributeSet, defStyleAttr){
    // 标识当前的手指个数
    private var mode = 0;
    private var mOldDist = 0f
    private var mTextSize = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (mTextSize == 0f){
            mTextSize = textSize;
        }
        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                mOldDist = 0f
                mode = 1
            }
            MotionEvent.ACTION_UP ->{
                mode = 0
                if (event.eventTime - event.downTime < ViewConfiguration.getTapTimeout())
                    performClick()
            }
            MotionEvent.ACTION_POINTER_UP -> {
                mode -= 1
            }
            MotionEvent.ACTION_POINTER_DOWN ->{
                mOldDist = spacing(event)
                mode += 1
            }
            MotionEvent.ACTION_MOVE -> {
                if (mode >= 2){
                    val newDist = spacing(event)
                    // 差值大于50时才认为是一次缩放操作
                    if (abs(newDist - mOldDist) > 50){
                        zoom(newDist/mOldDist)
                        mOldDist = newDist
                    }
                }
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private fun zoom(f: Float){
        mTextSize *= f
        setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
    }

    private fun spacing(event: MotionEvent): Float{
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt((x*x + y*y).toDouble()).toFloat()
    }
}