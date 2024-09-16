package com.jpc.chapter17.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.LinearLayout
import androidx.customview.widget.ViewDragHelper

class DragLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {
    private val mViewDragHelper: ViewDragHelper

    init {
        // 调用ViewDragHelper.create创建ViewDragHelper对象
        /**
         * ViewGroup forParent：即要拖动item的父控件的ViewGroup对象，在这里就是DragLayout。
         * float sensitivity：用于设置敏感度，一般设置成1.0。值越大，越敏感。
         * Callback cb：用于在拦截到消息后，将各种回调结果返回给用户，让用户操作item。
         */
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, object :ViewDragHelper.Callback(){
            /**
             * 主要用于决定需要拦截ViewGroup中的哪个控件的触摸事件，主要参数如下:
             * View child：当前用户触摸的子控件的View对象。
             * pointerId：当前触摸此控件的手指所对应的pointerId。
             * return boolean：返回值，表示是否对这个View进行各种事件的捕捉。
             * 如果返回值为true，那么就会对这个View进行捕捉，ViewDragHelper.Callback中的这些回调函数，
             * 在View发生变动时，都会有对应的回调反馈。
             * 如果返回值为false，那么就表示不对这个View进行捕捉，在手指触摸到这个View及这个View发生变动时，
             * ViewDragHelper.Callback中的回调函数不会有任何反馈。
             * 在上面的代码中，我们使用了return true，表示对所有的子View都进行捕捉。
             */
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return true
            }

            /**
             * 当手指在子View上横向移动时，会在这个函数中回调通知。
             * View child：当前手指横向移动所在的子View。
             * int left：当前子View如果跟随手指移动，那么它即将移动到的位置的left坐标值就是这里的left。
             * int dx：手指横向移动的距离。
             * return int：返回子View的新left坐标值，系统会把该子View的left坐标移动到这个位置。
             * 在代码中，我们使用的是return left，让View横向跟随手指移动。
             */
            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                return left
            }
            // 当手指在子View上纵向移动时，会在这个函数中回调通知。
            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                return top
            }
        })
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mViewDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_UP -> {
                if (event.eventTime - event.downTime < ViewConfiguration.getTapTimeout())performClick()
            }
        }
        // 一旦在ViewGroup的onTouchEvent函数中接收不到消息，ViewDragHelper的消息监听就会失效，它的各种回调函数就不会再执行了
        mViewDragHelper.processTouchEvent(event!!)
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}