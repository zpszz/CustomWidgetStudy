package com.jpc.chapter13.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.jpc.chapter13.R

class CameraImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr) {
    private lateinit var mBitmap: Bitmap
    private lateinit var mPaint: Paint
    private val camera = Camera()
    private val matrix = Matrix()
    private var mProgress: Float = 0f

    init {
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.cat)
        mPaint = Paint()
        mPaint.isAntiAlias = true
    }

    fun setProgress(progress: Float){
        mProgress = progress
        /**
         *  invalidate()  方法是View类的方法，用于标记当前View及其父级View（如果有）需要重绘。但是，
         *  invalidate()  方法只能在UI线程中调用，如果在非UI线程中调用  invalidate()  方法，则会抛出异常。
         *   postInvalidate()  方法是View类的方法的一个重载方法，与  invalidate()  方法不同的是，
         *   postInvalidate()  方法可以在任何线程中调用，因为它是通过Handler机制来实现的，
         *   会将重绘的任务post到UI线程的消息队列中，等待UI线程处理。
         */
        postInvalidate() // 立即更新视图的progress
    }

    // 需要注意，在使用Canvas和Camera后，都需要调用各自的restore函数来恢复到原始位置，避免这次的变更影响到下次。
    override fun onDraw(canvas: Canvas) {
        // 首先保存状态，方便后面恢复
        camera.save()
        canvas.save()
        // 在下层画一张半透明的图片, 用于显示图片的原始位置，而上层的图片才会随着Camera的变化而旋转
        mPaint.alpha = 100
        canvas.drawBitmap(mBitmap, 0f, 0f, mPaint)
        // rotateX、rotateY、rotateZ控制View绕单个坐标轴旋转，旋转画布，并应用于Canvas
        camera.rotateY(mProgress)
        // 控制View同时绕x y z轴旋转
        // camera.rotate(mProgress, mProgress, mProgress)
        // 三个参数分别代表沿着X轴、Y轴、Z轴方向移动
        //camera.translate(mProgress, 0f, 0f)

        /**
         * 通过camera.getMatrix（matrix）获取对应操作的matrix数组，然后通过调整
         * 旋转中心点的代码，将旋转中心点调整到图像中心点。
         */
//        camera.getMatrix(matrix)
//        val centerX = width/2.0f
//        val centerY = height/2.0f
//        matrix.preTranslate(-centerX, -centerY)
//        matrix.postTranslate(centerX, centerY)
//        canvas.setMatrix(matrix)

        // 更改Camera的位置
//        val centerX = width/2/72f
//        val centerY = height/2/72f
//        camera.setLocation(centerX, -centerY, camera.locationZ)
//        camera.getMatrix(matrix)
//        canvas.setMatrix(matrix)

        camera.applyToCanvas(canvas)
        camera.restore()
        /**
         * 利用ImageView自带的显示图片的函数super.onDraw（canvas），
         * 在已经旋转过的Canvas上绘图，此时绘制出来的图就是显示在上层的旋转过的图片了
         */
        super.onDraw(canvas)
        canvas.restore()
    }
}