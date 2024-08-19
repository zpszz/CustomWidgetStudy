package com.jpc.chapter15.widget

import android.content.Context
import android.view.MotionEvent
import android.view.ViewGroup

class OuterInterceptView(context: Context): ViewGroup(context){
    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

    }

    private fun fatherNeedClick():Boolean{
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var intercepted = false
        when(ev?.action){
            MotionEvent.ACTION_DOWN ->{
                intercepted = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (fatherNeedClick()){
                    // 父控件需要当前事件，那就拦截
                    intercepted = true
                }else{
                    intercepted = false
                }
            }
            MotionEvent.ACTION_UP ->{
                intercepted = false
            }
        }
        return intercepted
    }

    private fun selfNeedClick(): Boolean{
        return true
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action){
            MotionEvent.ACTION_MOVE -> {
                if(selfNeedClick()){
                    // 如果自己需要此类事件，就告诉父控件不能拦截
                    parent.requestDisallowInterceptTouchEvent(true)
                }else{
                    // 否则允许父控件拦截
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
        }
        // 其他事件情况交给父控件处理
        return super.dispatchTouchEvent(ev)
    }
}