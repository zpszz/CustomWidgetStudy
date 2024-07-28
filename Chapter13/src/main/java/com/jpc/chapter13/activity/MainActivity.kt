package com.jpc.chapter13.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter13.R
import com.jpc.chapter13.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener{
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.apply {
            btnTranslateActivity.setOnClickListener(this@MainActivity)
            btnRotate3dActivity.setOnClickListener(this@MainActivity)
            btnClockActivity.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(view: View?) {
        view?.let {
            when(view.id){
                R.id.btn_translate_activity -> {
                    val intent = Intent(this, TranslateActivity::class.java)
                    startActivity(intent)
                }
                R.id.btn_rotate3d_activity -> {
                    val intent = Intent(this, Rotate3DActivity::class.java)
                    startActivity(intent)
                }
                R.id.btn_clock_activity -> {
                    val intent = Intent(this, ClockViewActivity::class.java)
                    startActivity(intent)
                }

            }
        }
    }
}