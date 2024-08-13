package com.jpc.chapter14.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.Path.Direction
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat

class CustomRoundLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attributeSet, defStyleAttr) {
    private val mPath = Path()
    private var mBgColor: String = ""

    override fun onDraw(canvas: Canvas) {
        if (TextUtils.isEmpty(mBgColor)){
            if (background is ColorDrawable){
                val colorDrawable = background as ColorDrawable
                val color = colorDrawable.color
                mBgColor = "#${String.format("%08x", color)}"
            }
        }
        setBackgroundColor(Color.parseColor("#00FFFFFF"))
        super.onDraw(canvas)
    }

    override fun dispatchDraw(canvas: Canvas) {
        mPath.reset()
        mPath.addRoundRect(RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat()), 50f, 50f, Direction.CW)
        canvas.save()
        // 先将Canvas裁剪成有圆角的，这样画出来的控件也就具有了圆角
        canvas.clipPath(mPath)
        canvas.drawColor(Color.GREEN)
        if (!TextUtils.isEmpty(mBgColor)){
            canvas.drawColor((Color.parseColor(mBgColor)))
        }
        super.dispatchDraw(canvas)
        canvas.restore()
    }
}