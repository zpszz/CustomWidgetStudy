package com.jpc.customwidgetstudy.widget

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.jpc.customwidgetstudy.R

/**
 * 自定义Loading Dialog, 用于显示加载中的状态
 */
class LoadingDialog(context: Context): AlertDialog(context, R.style.Theme_AppCompat_Dialog){
    companion object{
        // 最短显示时间
        private const val MIN_SHOW_TIME = 500L
        // 最短延迟时间
        private const val MIN_DELAY_TIME = 500L
    }
    private var tvMessage: TextView
    init {
        val parent = (context as? Activity)?.findViewById<ViewGroup>(android.R.id.content)
        val loadView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, parent, false)
        setView(loadView)
        tvMessage = loadView.findViewById(R.id.tv_message)
    }
    // 记录开始时间
    private var mStartTime: Long = -1
    // 防止延时隐藏任务的重复执行
    private var mPostedHide: Boolean = false
    // 防止延时显示任务的重复执行
    private var mPostedShow: Boolean = false
    // 是否已经消失
    private var mDismissed: Boolean = false
    // 主线程Handler
    private val mHandler = Handler(Looper.getMainLooper())
    // 显示
    private val mDelayedShow: Runnable = Runnable {
        mPostedShow = false
        if (!mDismissed){
            mStartTime = System.currentTimeMillis()
            show()
        }
    }
    // 隐藏
    private val mDelayedHide: Runnable = Runnable {
        mPostedHide = false
        mStartTime = -1
        dismiss()
    }
    // 显示Dialog
    fun showDialog(message: String){
        tvMessage.text = message
        mStartTime = -1
        mDismissed = false
        mHandler.removeCallbacks(mDelayedHide)
        mPostedHide = false
        if (!mPostedShow){
            mHandler.postDelayed(mDelayedShow, MIN_DELAY_TIME)
            mPostedShow = true
        }
    }
    // 隐藏Dialog
    fun hideDialog(){
        mDismissed = true
        mHandler.removeCallbacks(mDelayedShow)
        mPostedShow = false
        val diff = System.currentTimeMillis() - mStartTime
        if (diff >= MIN_SHOW_TIME || mStartTime == -1L){
            dismiss()
        }else{
            if (!mPostedHide){
                mHandler.postDelayed(mDelayedHide, MIN_SHOW_TIME - diff)
                mPostedHide = true
            }
        }
    }
    // 从Window移除时移除所有的Callback
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeCallbacks(mDelayedHide)
        mHandler.removeCallbacks(mDelayedShow)
    }
}