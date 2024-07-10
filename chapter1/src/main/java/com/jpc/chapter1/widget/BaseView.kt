package com.jpc.chapter1.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Region
import android.graphics.RegionIterator
import android.util.AttributeSet
import android.view.View

class BaseView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, ) : View(context, attributeSet){
    private val paint = Paint()
    private val rect = Rect(20, 30 ,40 ,50)
    private val path: Path = Path() // 路径对象
    private val rectF = RectF(30f, 30f, 30f ,30f)
    private val region = Region(Rect(20, 30 ,40 ,50))
    // 在onDraw方法中不能创建变量
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.RED // 画笔的颜色
        paint.strokeWidth = 50f // 画笔的粗细
        paint.style = Paint.Style.STROKE // 填充样式
        //Paint.Style.FILL 仅填充内部
        //Paint.Style.FILL_ AND_ STROKE ：填充内部和描边
        //Paint.Style.STROKE 仅描边
        // 绘制一个圆形
        canvas.drawCircle(190f, 200f, 150f, paint)
        paint.color = Color.BLUE
        canvas.drawCircle(190f, 200f, 100f, paint)

        // 设置画布的颜色，三种方法功能差不多
        canvas.drawColor(0xFFFF00FF)
        canvas.drawARGB(0xFF, 0xFF, 0, 0xFF)
        canvas.drawRGB(255, 0, 255)

        // 绘制直线
        paint.color = Color.BLUE
        canvas.drawLine(100f, 100f, 200f, 200f, paint)

        // 绘制点
        paint.color = Color.YELLOW
        paint.strokeWidth = 10f
        canvas.drawPoint(300f, 300f, paint)

        // 绘制矩形
        canvas.drawRect(10f, 10f, 10f,10f, paint)
        // 先构造矩形再绘制
        canvas.drawRect(rect, paint)

        // 绘制三角形
        paint.color = Color.RED
        paint.strokeWidth = 5f
        paint.style = Paint.Style.STROKE
        path.moveTo(10f, 10f) // 路径起点
        path.lineTo(10f, 100f) // 第一条线的终点，也是第二条线的起点
        path.lineTo(300f, 100f) // 第二条线终点
        path.close() // 形成闭环
        canvas.drawPath(path, paint) // 在画布上绘制

        // 绘制弧线
        path.moveTo(10f, 10f)
        path.arcTo(rectF, 0f, 90f)
        canvas.drawPath(path, paint)

        // 绘制Region
        paint.color = Color.GREEN
        paint.strokeWidth = 4f
        paint.style = Paint.Style.FILL
        // 调用自定义的方法
        drawRegion(canvas, region, paint)
    }
    // 需要自定义一个绘制Region的方法
    private fun drawRegion(canvas: Canvas, rgn: Region, paint: Paint){
        val iter = RegionIterator(rgn)
        val r = Rect()
        while (iter.next(r)){
            canvas.drawRect(r, paint)
        }
    }
}