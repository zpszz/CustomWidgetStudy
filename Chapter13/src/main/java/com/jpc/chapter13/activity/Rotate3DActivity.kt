package com.jpc.chapter13.activity

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.DecelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter13.R
import com.jpc.chapter13.databinding.ActivityRotate3dBinding
import com.jpc.chapter13.widget.Rotate3dAnimation

class Rotate3DActivity : AppCompatActivity() {
    private val binding: ActivityRotate3dBinding by lazy { ActivityRotate3dBinding.inflate(layoutInflater) }
    private lateinit var openAnimation: Rotate3dAnimation
    private lateinit var closeAnimation: Rotate3dAnimation
    private val duration = 600L
    private var isOpen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_rotate_3d)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initOpenAnim()
        initCloseAnim()
        binding.btnOpen.setOnClickListener {
            if(openAnimation.hasStarted() && !openAnimation.hasEnded())return@setOnClickListener
            if(closeAnimation.hasStarted() && !closeAnimation.hasEnded())return@setOnClickListener
            if(isOpen){
                // 需要旋转的控件的根布局是XML中包裹ImageView的LinearLayout
                binding.llContent.startAnimation(closeAnimation)
            }else{
                binding.llContent.startAnimation(openAnimation)
            }
            isOpen = !isOpen
        }
    }

    private fun initOpenAnim(){
        openAnimation = Rotate3dAnimation(0f, 90f, true)
        openAnimation.duration = duration
        // 需要在完成动画之后，让View保持完成动画时的状态，所以要用到setFillAfter（true）函数
        openAnimation.fillAfter = true
        openAnimation.interpolator = AccelerateInterpolator() // 使用加速插值器
        openAnimation.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.apply {
                    ivCatLogo1.visibility = View.GONE
                    ivCatLogo2.visibility = View.VISIBLE
                    val rotateAnimation = Rotate3dAnimation(90f, 180f, false)
                    rotateAnimation.duration = duration
                    rotateAnimation.fillAfter = true
                    rotateAnimation.interpolator = DecelerateInterpolator() // 使用减速插值器
                    binding.llContent.startAnimation(rotateAnimation)
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
    }

    private fun initCloseAnim(){
        closeAnimation = Rotate3dAnimation(180f, 90f, true)
        closeAnimation.duration = duration
        closeAnimation.fillAfter = true
        closeAnimation.interpolator = AccelerateInterpolator()
        closeAnimation.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.apply {
                    ivCatLogo1.visibility = View.VISIBLE
                    ivCatLogo2.visibility = View.GONE
                }
                val rotateAnimation = Rotate3dAnimation(90f, 0f, false)
                rotateAnimation.duration = duration
                rotateAnimation.fillAfter = true
                rotateAnimation.interpolator = DecelerateInterpolator()
                binding.llContent.startAnimation(rotateAnimation)
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
    }
}