package com.jpc.chapter3.activity

import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter3.R
import com.jpc.chapter3.databinding.ActivityValueAnimatorBinding

class ValueAnimatorActivity : AppCompatActivity() {
    private val binding: ActivityValueAnimatorBinding by lazy { ActivityValueAnimatorBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_value_animator)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnValueAnimator.setOnClickListener {
            doAnimation()
        }
        binding.tvValueAnimator.setOnClickListener{
            Toast.makeText(this@ValueAnimatorActivity, "clicked me", Toast.LENGTH_SHORT).show()
        }
    }
    private fun doAnimation(){
        val animator = ValueAnimator.ofInt(0, 400)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            val tvValueAnimator = binding.tvValueAnimator
            // 通过layout函数改变TextView的位置
            tvValueAnimator.layout(animatedValue, animatedValue,
                animatedValue+tvValueAnimator.width, animatedValue+tvValueAnimator.height)
        }
        animator.start()
    }
}