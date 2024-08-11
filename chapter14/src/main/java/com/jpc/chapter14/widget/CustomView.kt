package com.jpc.chapter14.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.jpc.chapter14.R

class CustomView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attributeSet, defStyleAttr) {
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.cat)
    init {
        // 获得图片的实际宽高
        mWidth = mBitmap.width
        mHeight = mBitmap.height
    }

    /**
     * 重写onMeasure方法，设置View的宽高
     * @param widthMeasureSpec 宽度测量规格
     * @param heightMeasureSpec 高度测量规格
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 获得View的测量模式和大小
        val widthSpec = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpec = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthSpec == MeasureSpec.AT_MOST && heightSpec == MeasureSpec.AT_MOST){
            // 当设置为wrap_content时，View的宽高仍为其自身的实际宽高
            setMeasuredDimension(mWidth, mHeight)
        }else if(widthSpec == MeasureSpec.AT_MOST){
            setMeasuredDimension(mWidth, heightSize)
        }else if(heightSpec == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize, mHeight)
        }else{
            // 当设置为match_parent时，View的宽高为父容器的宽高
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mBitmap, 0f, 0f, null)
    }
}