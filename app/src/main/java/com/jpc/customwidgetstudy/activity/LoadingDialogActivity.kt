package com.jpc.customwidgetstudy.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.customwidgetstudy.R
import com.jpc.customwidgetstudy.databinding.ActivityLoadingDialogBinding
import com.jpc.customwidgetstudy.widget.LoadingDialog

class LoadingDialogActivity : AppCompatActivity() {
    private val binding: ActivityLoadingDialogBinding by lazy { ActivityLoadingDialogBinding.inflate(layoutInflater) }
    private val mHandler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loading_dialog)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.apply {
            btnLoading300.setOnClickListener{
                // 300ms后隐藏
                LoadingDialog(this@LoadingDialogActivity).apply {
                    showDialog("加载中...")
                    mHandler.postDelayed({hideDialog()},300)
                }
            }
            btnLoading1000.setOnClickListener{
                //3000ms后隐藏，方便演示
                LoadingDialog(this@LoadingDialogActivity).apply {
                    showDialog("加载中，请稍后...")
                    mHandler.postDelayed({hideDialog()},3000)
                }
            }
        }
    }

}