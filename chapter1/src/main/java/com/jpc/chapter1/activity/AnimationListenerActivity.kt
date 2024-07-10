package com.jpc.chapter1.activity

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter1.R
import com.jpc.chapter1.databinding.ActivityAnimationListenerBinding

class AnimationListenerActivity : AppCompatActivity() {
    private val binding: ActivityAnimationListenerBinding by lazy { ActivityAnimationListenerBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_animation_listener)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val rotateAnim = RotateAnimation(0f, -650f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnim.duration = 3000
        rotateAnim.fillAfter = true
        rotateAnim.interpolator = LinearInterpolator() // 线性插值器
        val scaleAnim = ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnim.duration = 700
        scaleAnim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                // 在动画开始时监听
            }

            override fun onAnimationEnd(p0: Animation?) {
                // 在动画结束时监听
                // 在缩放动画后执行旋转动画
                binding.tvAnimListener.startAnimation(rotateAnim)
            }

            override fun onAnimationRepeat(p0: Animation?) {
                // 在动画重复时监听
            }

        })
        binding.tvAnimListener.startAnimation(scaleAnim)
    }
}