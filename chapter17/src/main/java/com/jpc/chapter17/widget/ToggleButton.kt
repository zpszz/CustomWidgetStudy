package com.jpc.chapter17.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Scroller
import androidx.core.content.res.ResourcesCompat
import com.jpc.chapter17.R

class ToggleButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttr) {

    private var mSliderWidth = 0
    private var mScrollerWidth = 0

    private var mScroller: Scroller
    private var mIsOpen = false

    private var mLastX = 0

    init {
        setBackgroundResource(R.drawable.background)
        // 因为滑块需要单独响应点击和拖动事件，所以以ImageView的形式来添加它
        val slide = ImageView(context)
        slide.setBackgroundResource(R.drawable.slide)
        mScroller = Scroller(context)
        /**
         * 首先，定义一个mIsOpen变量，以标识当前滑块的位置，默认开关是关闭的，所以mIsOpen的初始值是false。
         * 然后，添加slide滑块的点击响应，根据当前滑块的位置，调用不同的startScroll函数。
         * 最后，改变mIsOpen变量的值，同时调用invalidate，以调用computeScroll函数。
         */
        slide.setOnClickListener {
            if (mIsOpen) {
                mScroller.startScroll(-mScrollerWidth, 0, mScrollerWidth, 0, 500)
            } else {
                mScroller.startScroll(0, 0, -mScrollerWidth, 0, 500)
            }
            mIsOpen = !mIsOpen
            invalidate()
        }
        addView(slide)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val bgDrawable = ResourcesCompat.getDrawable(resources, R.drawable.background, null)
        // 这里简单地将ViewGroup的宽度和高度设置的与背景图片的一致
        setMeasuredDimension(bgDrawable!!.intrinsicWidth, bgDrawable.intrinsicHeight)
    }

    /**
     * mSliderWidth变量表示滑块的宽度，我们为其取整个控件一半的大小，也就是背景图片宽度的一半；
     * mScrollerWidth变量表示滑块滑动到另一边的距离，在onLayout函数初始化时，滑块没有滑动，
     * 因此此时的滑动距离是整个控件宽度减去滑块本身的宽度。需要注意的是，这里的mScrollerWidth值为正。
     * 然后利用View view=getChildAt（0）；得到整个ToggleButton顶层的控件，
     * 即最后一个通过addView添加进来的控件，在这里就是滑块。最后，利用view.layout函数将滑块布局在左半边。
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        mSliderWidth = measuredWidth / 2
        mScrollerWidth = measuredWidth - mSliderWidth
        val view = getChildAt(0)
        view.layout(0, 0, mSliderWidth, measuredHeight)
    }

    /**
     * 在调用startScroll函数后，还需要在computeScroll函数中进行真正的处理，将滑块滑动到对应的位置
     */
    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            // 因为 scrollTo、scrollBy只移动其中的内容，不移动背景。所以这里只有滑块会移动
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { mLastX = it.x.toInt() }
        return ev?.action == MotionEvent.ACTION_MOVE // 只拦截移动操作
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x?.toInt() ?: 0
        when (event?.action) {
            // 在ACTION_DOWN消息到来时，判断滑动是否还没结束，如果还没结束，就强行将其结束
            MotionEvent.ACTION_DOWN -> {
                if (!mScroller.isFinished) mScroller.abortAnimation()
            }
            // 在ACTION_MOVE消息到来时，根据滑动距离，调用scrollBy函数来进行实时滑动
            MotionEvent.ACTION_MOVE -> {
                val deltaX = mLastX - x
                // 边界判断，防止滑块越界
                // 通过getScrollX函数能够获得当前的滑动距离，所以deltaX+getScrollX表示滑动后的位置，
                // 当deltaX+getScrollX（） ＞0 时，表示向左滑动的位置已经超过了初始位置，这时就要限制它，让它
                //不能超过初始位置
                if (deltaX + scrollX > 0){
                    scrollTo(0, 0)
                    return true
                }else if (deltaX + scrollX < -mSliderWidth){
                    // 当向右滑动的距离超过mSliderWidth，也就说明已经到达了最右边，此时依然需要限制，
                    // 因为向右滑动的距离dx取负值，所以滑动超过mSliderWidth距离的判断条件是
                    // deltaX+getScrollX（）＜- mSliderWidth。
                    scrollTo(-mScrollerWidth, 0)
                    return true
                }
                scrollBy(deltaX, 0)
            }

            MotionEvent.ACTION_UP -> {
                smoothScroll() // 在用户松手时，通过smoothScroll函数来实现滑动到目标位置的功能
                if (event.eventTime - event.downTime < ViewConfiguration.getTapTimeout()) performClick()
            }
        }
        mLastX = x
        return super.onTouchEvent(event)
    }

    /**
     * 在smoothScroll函数中，先设定一个边界点，当松手位置超过边界点时，向右滑动到右边缘，
     * 否则滑动到左边缘。这里的边界点位于整个滑块的1/4处，此处的滑动距离是-getMeasuredWidth（）/4。
     */
    private fun smoothScroll(){
        val bound = -measuredWidth / 4
        var deltaX = 0
        if (scrollX < bound){
            deltaX = -mScrollerWidth - scrollX
            if (!mIsOpen)mIsOpen = true
        }
        /**
         * 此时需要让滑块向左滑回初始位置，需要让它向左滑动-getScrollX距离。
         * 因为getScrollX的值为负，所以-getScrollX的值为正，表示向左滑动。
         */
        if (scrollX >= bound){
            deltaX = -scrollX
            if (mIsOpen)mIsOpen = false
        }
        mScroller.startScroll(scrollX, 0, deltaX, 0, 500)
        invalidate()
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}