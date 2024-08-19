package com.jpc.chapter15.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView

/**
 * 内部拦截法的应用
 * 顶部是一个TextView，而这个TextView的内容超出了显示区域，它的内容是可以滑动的。当手指在
 * TextView区域内上下滑时，TextView内部的内容也随着滑动，而当手指在其他区域上下滑时，整个ScrollView都在滑动。
 */
class CustomTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attributeSet, defStyleAttr) {
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_MOVE ->{
                // 在ACTION_MOVE消息到来时，TextView需要自己处理消息，所以禁止父控件拦截
                parent.requestDisallowInterceptTouchEvent(true)
            }
            else -> {

            }
        }
        return super.dispatchTouchEvent(event)
    }
}