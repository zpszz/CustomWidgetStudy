package com.jpc.chapter14.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * 自定义线性布局，继续自LinearLayout
 */
class CustomLinearLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val colorMatrix = ColorMatrix(floatArrayOf(
        0.213f, 0.715f, 0.072f, 0f, 0f,  // Red
        0.213f, 0.715f, 0.072f, 0f, 0f,  // Green
        0.213f, 0.715f, 0.072f, 0f, 0f,  // Blue
        0f, 0f, 0f, 1f, 0f   // Alpha
    ))

    private val mColorFilter = ColorMatrixColorFilter(colorMatrix)

    private var mBitmap: Bitmap? = null
    private lateinit var mPaint: Paint
    private lateinit var mCanvas: Canvas

    /**
     * 重写dispatchDraw方法，将LinearLayout的内容绘制到Bitmap上
     * 然后再将Bitmap绘制到Canvas上，这样就可以实现对LinearLayout的内容进行颜色过滤
     * 这个方法会在LinearLayout的子View绘制完成后调用，调用几次取决于LinearLayout的子View数量
     */
    override fun dispatchDraw(canvas: Canvas) {
        // 当mBitmap为null时进行初始化
        mBitmap = mBitmap ?: Bitmap.createBitmap(width, height, Config.ARGB_8888).apply {
            mCanvas = Canvas(this)
            mPaint = Paint().apply {
                colorFilter = mColorFilter
            }
        }
        canvas.save()
        super.dispatchDraw(canvas)
        canvas.drawBitmap(mBitmap!!, 0f, 0f, mPaint)
        canvas.restore()
    }
}