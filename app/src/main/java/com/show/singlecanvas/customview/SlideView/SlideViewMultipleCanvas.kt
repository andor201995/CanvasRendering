package com.show.singlecanvas.customview.SlideView

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import com.show.singlecanvas.customview.shapeView.ShapeView
import java.util.*

class SlideViewMultipleCanvas : FrameLayout {

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context) : super(context)

    private var numberOfShapes = 0
    private lateinit var slideLeftTop: FloatArray
    private val slideWidth =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300f, context.resources.displayMetrics)
    private val slideHeight =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, context.resources.displayMetrics)


    init {

        post {
            slideLeftTop = floatArrayOf((width - slideWidth) / 2, (height - slideHeight) / 2)

            val rand = Random()
            val randLeftTop = floatArrayOf(
                -50f, 50f,
                -60f, 60f,
                -70f, 70f,
                -80f, 80f,
                -90f, 90f,
                -100f, 100f
            )

            for (i in 0..numberOfShapes) {
                val shapeView = ShapeView(context)
                val randLeft = randLeftTop[rand.nextInt(12)]
                val randTop = randLeftTop[rand.nextInt(12)]
                shapeView.translationX = slideLeftTop[0] + randLeft
                shapeView.translationY = slideLeftTop[1] + randTop
                addView(shapeView)
            }
        }
    }

    fun setNumOfObjects(numberOfObjects: Int) {
        numberOfShapes = numberOfObjects
    }


}