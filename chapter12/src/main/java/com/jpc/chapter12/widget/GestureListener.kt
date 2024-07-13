package com.jpc.chapter12.widget

import android.nfc.Tag
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

/**
 * 实现手势监测
 */
class GestureListener: GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{
    private val Tag = "GestureListener"

    // 实现单击监听接口中的六个方法

    // 用户按下屏幕就会触发
    override fun onDown(e: MotionEvent): Boolean {
        Log.i(Tag, "onDown")
        return false
    }
    // 如果按下的时间超过瞬间，而且按下的时候没有松开或者拖动，就会触发
    override fun onShowPress(e: MotionEvent) {
        Log.i(Tag, "onShowPress")
    }
    // 一次单独的轻击抬起操作，轻击一下屏幕，立即抬起来，就会触发该函数
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        Log.i(Tag, "onSingleTapUp")
        return true
    }
    // 在屏幕上拖动事件，包括用手拖动View，以抛的动作滚动，都会多次触发该函数，在ACTION_MOVE时触发
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        Log.i(Tag, "onScroll")
        return true
    }
    // 长时间触摸屏幕，超过一定时长就会触发
    override fun onLongPress(e: MotionEvent) {
        Log.i(Tag, "onLongPress")
    }
    // 滑屏，用户按下触摸屏，快速移动后松开，由一个MotionEvent.ACTION_DOWN、多个ACTION_MOVE、一个ACTION_UP触发
    private val fling_min_distance = 100
    private val fling_min_velocity = 200
    override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        // 判断是向左滑动还是向右滑动
        e1?.let {
            if(it.x - e2.x > fling_min_distance && abs(velocityX) > fling_min_velocity){
                Log.i(Tag, "onFling: 向左滑动")
            }else if(e2.x - it.x > fling_min_distance && abs(velocityX) > fling_min_velocity){
                Log.i(Tag, "onFling: 向右滑动")
            }else{}
        }
        // 判断向上还是向下就使用velocityY
        return true
    }

    // 实现双击监听接口中的三个方法

    // 单击事件，用于判断是SingleTap，而不是DoubleTap，如果连续单击两次就是DoubleTap
    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        Log.d(Tag, "onSingleTapConfirmed")
        return true
    }

    // 双击事件
    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.d(Tag, "onDoubleTap")
        return true
    }

    // 双击间隔中发生的动作，指在触发onDoubleTap以后，在双击之间发生的其他动作，包含down、up、move事件
    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        Log.d(Tag, "onDoubleTapEvent")
        return true
    }
}