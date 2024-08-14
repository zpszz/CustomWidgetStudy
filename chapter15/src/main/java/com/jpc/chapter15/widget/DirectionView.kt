package com.jpc.chapter15.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import com.jpc.chapter15.R

class DirectionView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attributeSet, defStyleAttr), OnClickListener {
    private var lastX: Float = 0f
    private var lastY: Float = 0f

    init {
        // 将xml控件布局加入其中
        LayoutInflater.from(context).inflate(R.layout.direction_view_layout, this)
        findViewById<ImageView>(R.id.direction_up).setOnClickListener(this)
        findViewById<ImageView>(R.id.direction_down).setOnClickListener(this)
        findViewById<ImageView>(R.id.direction_left).setOnClickListener(this)
        findViewById<ImageView>(R.id.direction_right).setOnClickListener(this)
        findViewById<ImageView>(R.id.direction_ok).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.direction_up -> Toast.makeText(context, "up clicked", Toast.LENGTH_SHORT).show()
            R.id.direction_down -> Toast.makeText(context, "down clicked", Toast.LENGTH_SHORT).show()
            R.id.direction_left -> Toast.makeText(context, "left clicked", Toast.LENGTH_SHORT).show()
            R.id.direction_right -> Toast.makeText(context, "right clicked", Toast.LENGTH_SHORT).show()
            R.id.direction_ok -> Toast.makeText(context, "ok clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action){
            MotionEvent.ACTION_DOWN -> {
                // 记录下ACTION_DOWN消息的点击位置，因为不拦截ACTION_DOWN消息，
                // 所以移动手指的时候需要通过手指起点坐标来计算手指的移动距离
                // 得到了手指相对父控件左上边的坐标
                lastX = ev.x
                lastY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                // 在ACTION_MOVE消息到来时，直接返回true，以改变消息的流向，消息流向自己的onTouchEvent函数
                return true
            }
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        performClick()
        if (event?.action == MotionEvent.ACTION_MOVE){
            // 进行移动操作
            val offX = (event.x - lastX).toInt()
            val offY = (event.y - lastY).toInt()
            // 注意因为这里继承自androidx.appcompat.widget.LinearLayoutCompat
            // 所以DirectionView的直接父控件也要为androidx.appcompat.widget.LinearLayoutCompat
            val params = layoutParams as LayoutParams
            params.leftMargin += offX
            params.topMargin += offY
            layoutParams = params
            // 返回true，表示消费了消息，停止消息的传递
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}