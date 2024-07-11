package com.jpc.chapter3.activity

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter3.R
import com.jpc.chapter3.databinding.ActivityValueAnimatorMethodBinding

class ValueAnimatorMethodActivity : AppCompatActivity() {
    private val binding: ActivityValueAnimatorMethodBinding by lazy { ActivityValueAnimatorMethodBinding.inflate(layoutInflater) }
    private var repeatAnimator: Animator? = null
    private val TAG = "ValueAnimatorMethodActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_value_animator_method)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.apply {
            btnStartAnim.setOnClickListener{
                repeatAnimator = doRepeatAnim()
            }
            btnStopAnim.setOnClickListener{
                repeatAnimator?.cancel()
            }
        }
    }
    private fun doRepeatAnim(): ValueAnimator{
        val animator = ValueAnimator.ofInt(0, 400)
        animator.addUpdateListener {
            val curValue = animator.animatedValue as Int
            val tvAnimContent = binding.tvAnimContent
            tvAnimContent.layout(tvAnimContent.left, curValue, tvAnimContent.right, curValue + tvAnimContent.height)
        }
        // 监听动画的其他状态
        animator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator) {
                Log.d(TAG, "动画开始")
            }

            override fun onAnimationEnd(p0: Animator) {
                Log.d(TAG, "动画结束")
            }

            override fun onAnimationCancel(p0: Animator) {
                Log.d(TAG, "动画取消")
            }

            override fun onAnimationRepeat(p0: Animator) {
                Log.d(TAG, "动画重复")
            }

        })
        animator.repeatMode = ValueAnimator.REVERSE
        animator.repeatCount = ValueAnimator.INFINITE
        animator.duration = 1000
        animator.start()
        return animator
    }

    override fun onDestroy() {
        super.onDestroy()
        repeatAnimator?.removeAllListeners() // 移除所有的监听器
        repeatAnimator?.cancel() // 在Activity销毁时，设置为INFINITE（无限循环）的动画必须取消，否则内存泄漏
    }
}