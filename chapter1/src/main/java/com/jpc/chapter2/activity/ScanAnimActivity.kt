package com.jpc.chapter2.activity

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter2.R
import com.jpc.chapter2.databinding.ActivityScanAnimBinding

class ScanAnimActivity : AppCompatActivity() {
    private val binding: ActivityScanAnimBinding by lazy { ActivityScanAnimBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_scan_anim)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val animation1 = AnimationUtils.loadAnimation(this@ScanAnimActivity, R.anim.scale_alpha_anim)
        val animation2 = AnimationUtils.loadAnimation(this@ScanAnimActivity, R.anim.scale_alpha_anim)
        val animation3 = AnimationUtils.loadAnimation(this@ScanAnimActivity, R.anim.scale_alpha_anim)
        val animation4 = AnimationUtils.loadAnimation(this@ScanAnimActivity, R.anim.scale_alpha_anim)
        binding.tvStartScan.setOnClickListener{
            binding.ivCircle1.startAnimation(animation1)
            animation2.startOffset = 600 // 开始动画的时间延后600mm
            binding.ivCircle2.startAnimation(animation2)
            animation3.startOffset = 1200
            binding.ivCircle3.startAnimation(animation3)
            animation4.startOffset = 1800
            binding.ivCircle4.startAnimation(animation4)
        }
    }
}