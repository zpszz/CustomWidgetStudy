package com.jpc.chapter2.activity

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter2.R
import com.jpc.chapter2.databinding.ActivityFrameAnimXmlBinding

class FrameAnimXMLActivity : AppCompatActivity() {
    private val binding: ActivityFrameAnimXmlBinding by lazy { ActivityFrameAnimXmlBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_frame_anim_xml)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 取出背景动画
        val animation = binding.ivFrameXml.background as AnimationDrawable
        animation.start() // 开始播放动画

        // 通过代码方式动态添加帧动画，但是方法已过时
//        val animationDrawable = AnimationDrawable()
//        for (i in 1..14){
//            val identifier =
//                resources.getIdentifier("list_icon_gif_playing$i", "drawable", packageName)
//            val drawable = resources.getDrawable(identifier)
//            animationDrawable.addFrame(drawable, 60)
//        }
//        animationDrawable.isOneShot = false

    }
}