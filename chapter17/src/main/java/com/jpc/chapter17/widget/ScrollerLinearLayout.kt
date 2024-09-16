package com.jpc.chapter17.widget

import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.Scroller

class ScrollerLinearLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {
    private var mScroller: Scroller = Scroller(context, LinearInterpolator())

    fun startScroll(startX: Int, dx: Int){
        mScroller.startScroll(startX, 0, dx, 0)
        invalidate()
    }

    // 实时移动View
    override fun computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.currX, mScroller.currY)
        }
        invalidate()
    }
}