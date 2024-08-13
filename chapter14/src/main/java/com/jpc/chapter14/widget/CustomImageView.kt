package com.jpc.chapter14.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CustomImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr){

    private val colorMatrix = ColorMatrix(floatArrayOf(
        0.213f, 0.715f, 0.072f, 0f, 0f,  // Red
        0.213f, 0.715f, 0.072f, 0f, 0f,  // Green
        0.213f, 0.715f, 0.072f, 0f, 0f,  // Blue
        0f, 0f, 0f, 1f, 0f   // Alpha
    ))

    private val mColorFilter = ColorMatrixColorFilter(colorMatrix)

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        // 需要在ImageView执行默认绘图操作前执行setColorFilter函数
        colorFilter = mColorFilter
        super.onDraw(canvas)
        canvas.restore()
    }
}