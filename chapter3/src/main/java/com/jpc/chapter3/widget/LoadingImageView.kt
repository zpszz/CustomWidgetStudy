package com.jpc.chapter3.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.addListener
import androidx.core.content.res.ResourcesCompat
import com.jpc.chapter3.R

class LoadingImageView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null): AppCompatImageView(context, attributeSet) {
    private var mTop: Int = 0
    private var mCurImgIndex: Int= 0 // 当前动画图片索引
    private val mImgCount = 3 // 动画图片总张数
    init {
        val animator = ValueAnimator.ofInt(0, 100, 0)
        animator.repeatMode = ValueAnimator.RESTART
        animator.repeatCount = ValueAnimator.INFINITE
        animator.duration = 2000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener { animation ->
            val dx = animation.animatedValue as Int
            top = mTop - dx
        }
        animator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator) {
                // 动画开始时设置为第一张图片
                setImageDrawable(ResourcesCompat.getDrawable(resources ,R.drawable.pic_1, null))
            }

            override fun onAnimationEnd(p0: Animator) {
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
                mCurImgIndex++
                when(mCurImgIndex % mImgCount){
                    0 -> setImageDrawable(ResourcesCompat.getDrawable(resources ,R.drawable.pic_1, null))
                    1 -> setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.pic_2, null))
                    2 -> setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.pic_3, null))
                }
            }

        })
        // 开启动画
        animator.start()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        // 拿到控件的初始Top值
        mTop = top
    }
}