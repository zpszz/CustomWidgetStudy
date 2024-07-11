package com.jpc.chapter2.activity

import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter2.R
import com.jpc.chapter2.databinding.ActivityScaleAnimBinding

class ScaleAnimActivity : AppCompatActivity() {
    private val binding: ActivityScaleAnimBinding by lazy { ActivityScaleAnimBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_scale_anim)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnStartAnim.setOnClickListener {
            // 加载动画，这是scale缩放动画
            // 从xml加载动画布局参数
            val animation = AnimationUtils.loadAnimation(this@ScaleAnimActivity, R.anim.scale_anim)
            // 使用代码构造临时动画
            val scaleAnim = ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            scaleAnim.duration = 700 // 设置动画播放时长
            // 开始动画
            binding.tvScaleAnim.startAnimation(animation) // scaleAnim
        }
        binding.btnStartAnimSet.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this@ScaleAnimActivity, R.anim.set_anim)
            // 播放动画集，是一个从小到大，从无到有，旋转出场的动画
            binding.tvAnimSet.startAnimation(animation)

            // 使用代码构建同样的动画
            val alphaAnim = AlphaAnimation(0.0f, 1.0f)
            val scaleAnim = ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            val rotateAnim = RotateAnimation(0f, 720f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            val animationSet = AnimationSet(true)
            animationSet.addAnimation(alphaAnim)
            animationSet.addAnimation(scaleAnim)
            animationSet.addAnimation(rotateAnim)
            animationSet.duration = 3000
            animationSet.fillAfter = true

        }
    }
}