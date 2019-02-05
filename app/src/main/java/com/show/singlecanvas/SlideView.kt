package com.show.singlecanvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class SlideView : View {

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context) : super(context)

    private val slideWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300f, context.resources.displayMetrics)
    private val slideHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, context.resources.displayMetrics)
    private val slidePaint = Paint()

    private val rectOneWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60f, context.resources.displayMetrics)
    private val rectOneHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, context.resources.displayMetrics)
    private val rectOnePaint = Paint()

    private val rectSecondWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, context.resources.displayMetrics)
    private val rectSecondHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120f, context.resources.displayMetrics)
    private val rectSecondPaint = Paint()


    init {
        slidePaint.style = Paint.Style.FILL
        slidePaint.color = Color.GRAY
        rectOnePaint.style = Paint.Style.FILL
        rectOnePaint.color = Color.RED
        rectSecondPaint.style = Paint.Style.FILL
        rectSecondPaint.color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val original = canvas!!.save()

        //translate to the center of screen
        canvas.translate((width - slideWidth) / 2, (height - slideHeight) / 2)
        //Draw Slide
        canvas.drawRect(0f, 0f, slideWidth, slideHeight, slidePaint)

        //DrawShapes one
        canvas.drawRect(0f, 0f, rectOneWidth, rectOneHeight, rectOnePaint)

        canvas.save()
        canvas.translate(100f, 70f)
        //DrawShapes two
        canvas.drawRect(0f, 0f, rectSecondWidth, rectSecondHeight, rectSecondPaint)
        canvas.restore()

        canvas.restore()
    }
}