package com.jpc.chapter14.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import com.jpc.chapter14.R

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attributeSet, defStyleAttr) {
    private var mDeleteDrawable: Drawable

    init {
        // 获取控件资源
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.CustomEditText)
        val icDeleteResId =
            typedArray.getResourceId(R.styleable.CustomEditText_ic_delete, R.drawable.delete)
        mDeleteDrawable = resources.getDrawable(icDeleteResId)
        mDeleteDrawable.setBounds(0, 0, 80, 80)
        // 用完之后要释放资源，防止内存泄漏
        typedArray.recycle()
    }

    // 输入框内容改变时调用
    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        // 当EditText具有焦点并且其中有文字时，显示删除图标
        setDeleteIconVisible(hasFocus() && text?.isNotEmpty() == true)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        setDeleteIconVisible(focused && length() > 0)
    }

    @SuppressLint("SetTextI18n")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                if (event.x <= (width - paddingRight) && event.x >= (width - paddingRight - mDeleteDrawable.bounds.width())) {
                    setText("")
                }
            }
        }
        performClick()
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private fun setDeleteIconVisible(deleteVisible: Boolean) {
        // 当入参deleteVisible为true时，就利用setCompoundDrawables在EditText右侧显示删除图标，否则不显示。
        setCompoundDrawables(null, null, if (deleteVisible) mDeleteDrawable else null, null)
        invalidate()
    }
}