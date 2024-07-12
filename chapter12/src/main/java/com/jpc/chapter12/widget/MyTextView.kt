package com.jpc.chapter12.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.jpc.chapter12.R

@SuppressLint("SetTextI18n")
class MyTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null): AppCompatTextView(context, attributeSet){
    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MyTextView)
        val headerHeight = typedArray.getDimension(R.styleable.MyTextView_header_height, -1f)
        val age = typedArray.getInt(R.styleable.MyTextView_age, -1)
        typedArray.recycle() // 在使用完后需要释放资源
        this.text = "headerHeight: $headerHeight, age: $age"
    }
}