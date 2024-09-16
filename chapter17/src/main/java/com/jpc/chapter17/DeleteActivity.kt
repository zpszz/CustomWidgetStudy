package com.jpc.chapter17

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter17.databinding.ActivityDeleteBinding

class DeleteActivity : AppCompatActivity(), OnClickListener{
    companion object{
        private const val DISTANCE = 300
    }
    private val binding: ActivityDeleteBinding by lazy { ActivityDeleteBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_delete)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.scrollTo.setOnClickListener(this)
        binding.scrollBy.setOnClickListener(this)
        binding.scrollReset.setOnClickListener(this)

        binding.scrollStart.setOnClickListener(this)
        binding.scrollReset2.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            // 不使用自定义布局
            R.id.scroll_to -> binding.llActionRoot.scrollTo(50, 0)
            R.id.scroll_by -> binding.llActionRoot.scrollBy(50, 0)
            R.id.scroll_reset -> binding.llActionRoot.scrollTo(0, 0)

            // 使用自定义布局ScrollerLinearLayout
            R.id.scroll_start -> binding.sllActionRoot.startScroll(0, DISTANCE)
            R.id.scroll_reset2 -> binding.sllActionRoot.startScroll(DISTANCE, -DISTANCE)
        }
    }
}