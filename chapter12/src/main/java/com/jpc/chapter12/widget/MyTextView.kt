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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)
        val measureWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val measureHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        // 计算控件实际的大小
        val width = 0
        val height = 0
        setMeasuredDimension(if(measureWidthMode == MeasureSpec.EXACTLY) measureWidth else width,
            if(measureHeightMode == MeasureSpec.EXACTLY) measureHeight else height)
    }
}