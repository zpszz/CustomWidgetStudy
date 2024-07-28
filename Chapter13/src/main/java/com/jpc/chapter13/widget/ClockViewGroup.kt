package com.jpc.chapter13.widget

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.BounceInterpolator
import androidx.appcompat.widget.LinearLayoutCompat

// 用于表示最大旋转角度
private const val MAX_ROTATE_DEGREE = 20f
class ClockViewGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attributeSet, defStyleAttr) {

    private var mCenterX: Float = 0f
    private var mCenterY: Float = 0f
    private var mCanvasRotateX: Float = 0f
    private var mCanvasRotateY: Float = 0f
    private var mMatrixCanvas = Matrix()
    private val mCamera = Camera()
    // 数值动画
    private var mSteadyAnim: ValueAnimator? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        /**
         * 通过w/2和h/2来获取最新的控件宽度和高度，以计算出控件中心点位置。
         */
        mCenterX = w/2f
        mCenterY = h/2f
    }

    override fun dispatchDraw(canvas: Canvas) {
        mMatrixCanvas.reset()
        mCamera.save()
        // 将ViewGroup中的控件旋转mCanvasRotateX和mCanvasRotateY角度
        mCamera.rotateX(mCanvasRotateX)
        mCamera.rotateY(mCanvasRotateY)
        mCamera.getMatrix(mMatrixCanvas)
        mCamera.restore()
        // 将中心点移动到图像中心点
        mMatrixCanvas.preTranslate(-mCenterX, -mCenterY)
        mMatrixCanvas.postTranslate(mCenterX, mCenterY)

        canvas.save()
        canvas.setMatrix(mMatrixCanvas)
        super.dispatchDraw(canvas)
        canvas.restore()
    }

    override fun performClick(): Boolean {
        // Call the super implementation of performClick
        super.performClick()
        // Add any custom click handling logic here if needed
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var x = 0f
        var y = 0f
        event?.let {
            x = it.x
            y = it.y
        }
        val action = event?.actionMasked
        when(action){
            MotionEvent.ACTION_DOWN -> {
                // 如果有动画，就先取消动画
                cancelSteadyAnimIfNeed()
                rotateCanvasWhenMove(x, y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                rotateCanvasWhenMove(x, y)
                return true
            }
            MotionEvent.ACTION_UP -> {
                startNewSteadyAnim()
                performClick()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun rotateCanvasWhenMove(x: Float, y : Float){
        val dx = x - mCenterX
        val dy = y - mCenterY
        var percentX = dx / (width/2f)
        var percentY = dy / (height/2f)
        if (percentX > 1f){
            percentX = 1f
        }else if (percentX < -1f){
            percentX = -1f
        }
        if (percentY > 1f){
            percentY = 1f
        }else if (percentY < -1f){
            percentY = -1f
        }
        mCanvasRotateX = MAX_ROTATE_DEGREE*percentX
        mCanvasRotateY = MAX_ROTATE_DEGREE*percentY
        postInvalidate()
    }

    /**
     * 当抬起手指的时候，需要让图像复位，但这里需要注意，旋转时会同时改变rotateX和rotateY，
     * 所以在复位时，也需要同时将这两个变量复位
     * propertyName：属性名，在动画过程中，我们将使用属性名来获取对应的值。
     * float...values：代表数据的变换过程，其中3个点表示可变长度参数列表，即可以传入用逗号间隔的多个值。
     * 在这里，我们只传入两个值（mCanvasRotateX，0），表示数值的变化过程是从mCanvasRotateX变为0。
     */
    private fun startNewSteadyAnim(){
        val propertyNameRotateX = "mCanvasRotateX"
        val propertyNameRotateY = "mCanvasRotateY"
        val holderRotateX = PropertyValuesHolder.ofFloat(propertyNameRotateX, mCanvasRotateX, 0f)
        val holderRotateY = PropertyValuesHolder.ofFloat(propertyNameRotateY, mCanvasRotateY, 0f)
        mSteadyAnim = ValueAnimator.ofPropertyValuesHolder(holderRotateX, holderRotateY)
        mSteadyAnim?.let {
            it.duration = 1000
            it.interpolator = BounceInterpolator()
            it.addUpdateListener { animation ->
                mCanvasRotateX = (animation.getAnimatedValue(propertyNameRotateX)) as Float
                mCanvasRotateY = (animation.getAnimatedValue(propertyNameRotateY)) as Float
                postInvalidate()
            }
            it.start()
        }
    }

    private fun cancelSteadyAnimIfNeed(){
        mSteadyAnim?.let {
            if( it.isStarted || it.isRunning){
                it.cancel()
            }
        }
    }
}