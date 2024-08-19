package com.jpc.chapter15.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import com.jpc.chapter15.R

/**
 * 外部拦截法
 */
class CustomScrollView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ScrollView(context, attributeSet, defStyleAttr) {
    private var mDownPointY = 0f
    private var mConflictHeight = 0

    init {
        // 得到顶部TextView固定高度所对应的px值
        mConflictHeight = context.resources.getDimensionPixelSize(R.dimen.conflict_height)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var intercepted = false
        when(ev?.action){
            MotionEvent.ACTION_DOWN -> {
                intercepted = false
                mDownPointY = ev.y
            }
            MotionEvent.ACTION_MOVE ->{
                // 当ACTION_MOVE消息到来时，判断当前手指位置是不是在顶部TextView的内部，如果在内部，则不拦截消息
                if (mDownPointY < mConflictHeight){
                    intercepted = false
                }else{
                    intercepted = true
                }
            }
            MotionEvent.ACTION_UP -> {
                intercepted = false
            }
        }
        return intercepted
    }
}