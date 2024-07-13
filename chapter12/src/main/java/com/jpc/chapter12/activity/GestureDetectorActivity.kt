package com.jpc.chapter12.activity

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter12.R
import com.jpc.chapter12.databinding.ActivityGestureDetectorBinding
import com.jpc.chapter12.widget.GestureListener

class GestureDetectorActivity : AppCompatActivity(), OnTouchListener{
    private val binding: ActivityGestureDetectorBinding by lazy { ActivityGestureDetectorBinding.inflate(layoutInflater) }
    private lateinit var mGestureDetector: GestureDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_gesture_detector)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mGestureDetector = GestureDetector(this ,GestureListener())
        binding.tvGestureDetector.apply {
            setOnTouchListener(this@GestureDetectorActivity)
            isFocusable = true
            isClickable = true
            isLongClickable = true
        }

    }

    override fun onTouch(view: View?, e: MotionEvent?): Boolean {
        view?.performClick()
        return mGestureDetector.onTouchEvent(e!!)
    }
}