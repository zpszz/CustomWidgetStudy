package com.jpc.chapter3.widget

import android.animation.TimeInterpolator

// 自定义插值器
class MyInterpolator: TimeInterpolator{
    override fun getInterpolation(input: Float): Float {
        return 1 - input
    }
}