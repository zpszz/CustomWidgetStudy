package com.jpc.chapter12.activity

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter12.R
import com.jpc.chapter12.databinding.ActivityTencentRocketBinding

class TencentRocketActivity : AppCompatActivity(), OnTouchListener, OnClickListener{
    private val binding: ActivityTencentRocketBinding by lazy { ActivityTencentRocketBinding.inflate(layoutInflater) }
    private lateinit var mImageView: ImageView
    private lateinit var mLayoutParams: WindowManager.LayoutParams
    private lateinit var mWindowManager: WindowManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_tencent_rocket)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        startActivityForResult(intent, 100)
    }

    private fun initView(){
        binding.btnAddView.setOnClickListener(this)
        binding.btnRemoveView.setOnClickListener(this)
        mWindowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    override fun onTouch(view: View?, e: MotionEvent?): Boolean {
        var rawX = 0f
        var rawY = 0f
        e?.let {
            rawX = it.rawX
            rawY = it.rawY
        }
        when(e?.action){
            MotionEvent.ACTION_MOVE -> {
                mLayoutParams.x = rawX.toInt()
                mLayoutParams.y = rawY.toInt()
                mWindowManager.updateViewLayout(mImageView, mLayoutParams)
            }
        }
        return false
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_add_view -> {
                mImageView = ImageView(this)
                mImageView.setBackgroundResource(R.mipmap.ic_launcher)
                mLayoutParams = WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,2099,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                    PixelFormat.TRANSPARENT
                )
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
                mLayoutParams.gravity = Gravity.TOP or Gravity.START
                mLayoutParams.x = 0
                mLayoutParams.y = 300
                mImageView.setOnTouchListener(this)
                mWindowManager.addView(mImageView, mLayoutParams)
            }
            R.id.btn_remove_view -> {
                mWindowManager.removeViewImmediate(mImageView)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100){
            initView()
        }
    }
}