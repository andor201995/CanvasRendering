package com.show.singlecanvas.customview.slideView

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import com.show.singlecanvas.customview.shapeView.ShapeView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList

class SlideViewMultipleCanvas : FrameLayout {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context) : super(context)

    private val listOfObject = ArrayList<WeakReference<ShapeView>>()
    private lateinit var slideLeftTop: FloatArray
    private val slideWidth =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            300f,
            context.resources.displayMetrics
        )
    private val slideHeight =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            200f,
            context.resources.displayMetrics
        )

    private suspend fun setUpView() {
        withContext(Dispatchers.Default) {
            slideLeftTop =
                floatArrayOf(slideWidth / 2, slideHeight / 2)

            val rand = Random()
            val randLeftTop = floatArrayOf(
                -50f, 50f,
                -60f, 60f,
                -70f, 70f,
                -80f, 80f,
                -90f, 90f,
                -100f, 100f
            )

            val isSettingShape = async(Dispatchers.Default) { setShapeView(randLeftTop, rand) }
            val isAddingShape = async(Dispatchers.Main) { addViewInScope() }

            if (isAddingShape.await() && isSettingShape.await()) {
                listOfObject.clear()
            }
        }
    }

    private fun setShapeView(randLeftTop: FloatArray, rand: Random): Boolean {
        for (shapeView in listOfObject) {
            val randLeft = randLeftTop[rand.nextInt(12)]
            val randTop = randLeftTop[rand.nextInt(12)]
            shapeView.get()?.translationX = slideLeftTop[0] + randLeft
            shapeView.get()?.translationY = slideLeftTop[1] + randTop
        }
        return true
    }

    private fun addViewInScope(): Boolean {
        for (shapeView in listOfObject) {
            shapeView.get()?.let {
                addView(it)
            }
        }
        return true
    }

    suspend fun setNumOfObjects(numberOfObjects: Int) {
        withContext(Dispatchers.Default) {
            for (i in 0 until numberOfObjects) {
                listOfObject.add(WeakReference(ShapeView(context)))
            }
        }
    }

    suspend fun startJob() {
        setUpView()
    }
}