package com.jpc.chapter13.widget

import android.graphics.Camera
import android.view.animation.Animation
import android.view.animation.Transformation

class Rotate3dAnimation(
    private val fromDegrees: Float,
    private val endDegrees: Float,
    private val reverse: Boolean
) : Animation() {
    private var mCenterX: Float = 0f
    private var mCenterY: Float = 0f
    private lateinit var mCamera: Camera
    private val mDepthZ = 400

    /**
     * initialize函数会在执行动画前调用，参数中的width、height表示将
     * 要执行动画的View的宽和高，parentWidth、parentHeight表示执行动画
     * 的View的父控件的宽和高。因为该函数会在执行动画前调用，所以一般会在该函数中执行一些初始化操作。
     */
    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        /**
         * 要围绕控件中心点旋转，因此需要获取控件中心点的位置坐标。所以，在初始化时，计算出控件中心点的位置坐标
         */
        mCenterX = width / 2f
        mCenterY = height / 2f
        mCamera = Camera()
    }

    /**
     * applyTransformation函数最重要，它就是用来实现自定义Animation的函数，相关参数如下。
     * float interpolatedTime：正在执行的Animation的当前进度，取值范围为0～1。
     * Transformation t：当前进度下，需要对控件应用的变换操作都保存在Transformation中。
     */
    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        // 根据当前进度计算出当前的旋转角度
        val degrees = fromDegrees + ((endDegrees - fromDegrees) * interpolatedTime)
        // 利用Camera将图片绕Y轴旋转degrees的角度
        mCamera.save()
        val z : Float
        /**
         * 当mReverse为true时，View沿Z轴的移动距离随动画的播放而增大，在动画结束（interpolatedTime等于1）时达到最大。
         * 当mReverse为false时，View沿Z轴的移动距离随动画的播放而减小，在动画结束时，View沿Z轴的移动距离回归到0。
         * 这样可以解决： 图像旋转时的大小跟其与Z轴的距离有关，View与Camera的距离越大，显示的图像越小 引起的问题
         */
        if(reverse){
            z = mDepthZ*interpolatedTime
            mCamera.translate(0f, 0f, z)
        }else{
            z = mDepthZ*(1.0f - interpolatedTime)
            mCamera.translate(0f, 0f, z)
        }
        val matrix = t?.matrix
        mCamera.rotateY(degrees)
        mCamera.getMatrix(matrix)
        mCamera.restore()
        // 将旋转中心移到控件中心点位置
        matrix?.preTranslate(-mCenterX, -mCenterY)
        matrix?.postTranslate(mCenterX, mCenterY)
        // 执行改变过的动画操作，以将操作最终体现在控件上。
        super.applyTransformation(interpolatedTime, t)
    }
}