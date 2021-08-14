package com.setianjay.mycustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class MyButton: AppCompatButton {
    private var enabledBackground: Drawable? = null
    private var disableBackground: Drawable? = null

    private var txtColor: Int = 0

    constructor(context: Context): super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)
    {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr){
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
        background = when{
            isEnabled -> enabledBackground
            else -> disableBackground
        }


        isAllCaps = true
        setTextColor(txtColor)
        textSize = 16F
        gravity = Gravity.CENTER
        text = when{
            isEnabled -> "Submit"
            else -> "Input Please"
        }
    }

    /**
     * description      : function for handle all resources initialize and listener of view
     * params in        :
     * params out       :
     * variable         :
     * return           : Unit
     * note             : use this function for initialize resources and set up listener for view
     * */
    private fun init(){
        enabledBackground = ResourcesCompat.getDrawable(resources,R.drawable.bg_button, null)
        disableBackground = ResourcesCompat.getDrawable(resources,R.drawable.bg_button_disable, null)

        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
    }
}