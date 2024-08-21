package com.jpc.chapter16.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MultiTouchView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {
    // 用于判断第2根手指是否存在
    private var haveSecondPoint = false
    // 记录第2根手指的位置
    private var point = PointF(0f, 0f )
    private val mDefaultPaint = Paint()

    init {
        mDefaultPaint.color = Color.WHITE
        mDefaultPaint.isAntiAlias = true // 抗锯齿, 使图像更加平滑
        mDefaultPaint.textAlign = Paint.Align.CENTER
        mDefaultPaint.textSize = 30f
    }

    /**
     * 在用户按下手指时，需要加以判断，当前是第几根手指，然后获取第2根手指的位置
     * 道PointerIndex是变化的，而PointerId是不变的，PointerId根据手指按下的顺序从0到1逐渐增加。
     * 因此，第2根手指的PointerId就是1
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        performClick()
        val index = event?.actionIndex
        when(event?.actionMasked){
            MotionEvent.ACTION_POINTER_DOWN ->{
                index?.let {
                    if (event.getPointerId(it) == 1){
                        haveSecondPoint = true
                        point.set(event.x, event.y)
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                try {
                    if (haveSecondPoint){
                        // 在异常情况下可能出现越界，所以使用try…catch…来进行保护
                        val pointerIndex = event.findPointerIndex(1)
                        point.set(event.getX(pointerIndex), event.getY(pointerIndex))
                    }
                }catch (e: Exception){
                    haveSecondPoint = false
                }
            }
            MotionEvent.ACTION_POINTER_UP -> {
                index?.let {
                    if (event.getPointerId(it) == 1)haveSecondPoint = false
                }
            }
            MotionEvent.ACTION_UP -> {
                haveSecondPoint = false
            }
        }
        // 重绘界面
        invalidate()
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    /**
     * 在重绘界面时，主要是在point中存储的第2根手指的位置处画一个白色圆圈
     */
    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.GREEN)
        if (haveSecondPoint){
            canvas.drawCircle(point.x, point.y, 50f, mDefaultPaint)
        }
        canvas.save()
        // 在布局的中间位置写上提示文字
        canvas.translate(measuredWidth/2f, measuredHeight/2f)
        // x y 是指定的绘制文本的起始位置
        canvas.drawText("追踪第二个按下的手指的位置", 0f,0f, mDefaultPaint)
        canvas.restore()
    }
}