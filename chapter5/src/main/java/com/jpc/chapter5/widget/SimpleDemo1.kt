package com.jpc.chapter5.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.util.Log
import android.view.View

class SimpleDemo1 @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null): View(context, attributeSet) {
    private val TAG = "SimpleDemo1"
    private var paint: Paint = Paint()
    private val path = Path()
    private val measure1 = PathMeasure(path, false)
    private val measure2 = PathMeasure(path, true) // 强制闭合
    private val measure3 = PathMeasure(path, false)
    init {
        paint.color = Color.BLACK
        paint.strokeWidth = 8f
        paint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(50f, 50f)

        path.moveTo(0f, 0f)
        path.lineTo(0f, 100f)
        path.lineTo(100f, 100f)
        path.lineTo(100f, 0f)

        // getLength()计算单条曲线的长度
        Log.e(TAG, "forceClosed=${measure1.isClosed} --> ${measure1.length}")
        // 强制闭合后，会计算原本不闭合的线条长度
        Log.e(TAG, "forceClosed=${measure2.isClosed} --> ${measure2.length}")
        canvas.drawPath(path, paint)

        canvas.translate(150f, 150f)
        path.addRect(-50f, -50f, 50f, 50f, Path.Direction.CW)
        path.addRect(-100f, -100f, 100f, 100f, Path.Direction.CW)
        path.addRect(-120f, -120f, 120f, 120f, Path.Direction.CW)
        canvas.drawPath(path, paint)

        do {
            val len = measure3.length
            Log.d(TAG, "forceClosed=${measure3.isClosed} --> $len")
        }while (measure3.nextContour()) // 可以计算多条曲线的长度
    }
}