package com.setianjay.mycustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class MyEditText : AppCompatEditText, View.OnTouchListener {
    internal var mClearButtonImage: Drawable? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    /**
     * description      : function when the view has to render its content
     * params in        : canvas: Canvas?
     * params out       :
     * variable         :
     * return           : Unit
     * note             : use this function for setup view property
     * */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = "Enter your name"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    /**
     * description      : function for handle all resources initialize and listener of view
     * params in        :
     * params out       :
     * variable         :
     * return           : Unit
     * note             : use this function for initialize resources and set up listener for view
     * */
    private fun init() {
        mClearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_black_clear_24)
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun showClearButton() {
        // to set drawable icon in the end edge of view
        setCompoundDrawablesWithIntrinsicBounds(null, null, mClearButtonImage, null)
    }

    private fun hideClearButton() {
        // to remove the drawable icon in the view
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    }


    /**
     * description      : function for handle when view is pressed
     * params in        : - v: View?        - event: MotionEvent?
     * params out       :
     * variable         :
     * return           : Boolean
     * note             : if view is pressed or touched, this function will running
     * */
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (compoundDrawables[2] != null){
            val clearButtonStart: Float
            val clearButtonEnd: Float

            var isClearButtonClicked = false
            when(layoutDirection){
                View.LAYOUT_DIRECTION_RTL -> { // RTL(Right to Left)
                    Log.e("EditText", "RTL RUNNING")
                    clearButtonEnd = (mClearButtonImage?.intrinsicWidth!! + paddingStart).toFloat()
                    when{
                        event?.x!! < clearButtonEnd -> isClearButtonClicked = true
                    }
                }
                else -> {
                    Log.e("EditText", "LTR RUNNING")
                    clearButtonStart = (width - paddingEnd - mClearButtonImage?.intrinsicWidth!!).toFloat()
                    when{
                        event?.x!! > clearButtonStart -> isClearButtonClicked = true
                    }
                }
            }
            when {
                isClearButtonClicked -> when (event?.action) {
                    MotionEvent.ACTION_DOWN -> { // when view is pressed
                        Log.e("EditText", "ACTION_DOWN")
                        showClearButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> { // when view is released after pressed
                        Log.e("EditText", "ACTION_UP")
                        when {
                            text != null -> text?.clear()
                        }
                        hideClearButton()
                        return true
                    }
                    else -> return false
                }
                else -> return false
            }
        }
        return false
    }

}