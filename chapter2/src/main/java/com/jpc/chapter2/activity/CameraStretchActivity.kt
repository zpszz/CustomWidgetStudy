package com.jpc.chapter2.activity

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter2.R
import com.jpc.chapter2.databinding.ActivityCameraStretchBinding

class CameraStretchActivity : AppCompatActivity() {
    private val binding: ActivityCameraStretchBinding by lazy { ActivityCameraStretchBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_camera_stretch)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val scaleAnim = ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnim.fillAfter = true
        scaleAnim.interpolator = BounceInterpolator() // 设置为回弹插值器
        scaleAnim.duration = 6000
        binding.ivSplash.startAnimation(scaleAnim)
    }
}