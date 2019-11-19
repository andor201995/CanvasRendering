package com.show.singlecanvas.customview.shapeView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.TypedValue
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.show.singlecanvas.customview.MyEditText
import java.util.*


/**
 * Created by anmol-5732 on 05/01/18.
 */
class ShapeView(context: Context) : FrameLayout(context) {


    companion object {
        private val INVALID_POINTER_ID: Int = -1
    }

    private var activePointerID: Int = INVALID_POINTER_ID
    private var paint: Paint = Paint()
    private var path: Path = Path()
    private var initX: Float = 0.0f
    private var initY: Float = 0.0f


    init {
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.isAntiAlias = true
        paint.isDither = true
        val rand = Random()

        val randDim = floatArrayOf(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                50f,
                context.resources.displayMetrics
            ),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                60f,
                context.resources.displayMetrics
            ),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                70f,
                context.resources.displayMetrics
            ),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                80f,
                context.resources.displayMetrics
            ),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                90f,
                context.resources.displayMetrics
            ),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                100f,
                context.resources.displayMetrics
            ),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                110f,
                context.resources.displayMetrics
            ),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                120f,
                context.resources.displayMetrics
            )
        )

        val randColor = intArrayOf(
            Color.BLACK,
            Color.YELLOW,
            Color.CYAN,
            Color.DKGRAY,
            Color.GREEN,
            Color.LTGRAY,
            Color.MAGENTA,
            Color.RED
        )

        val randWidth = randDim[rand.nextInt(8)]
        val randHeight = randDim[rand.nextInt(8)]
        paint.color = randColor[rand.nextInt(8)]
        path.addRect(0f, 0f, randWidth, randHeight, Path.Direction.CW)
        setBackgroundColor(paint.color)
        layoutParams = ViewGroup.LayoutParams(randWidth.toInt(), randHeight.toInt())
        val text = MyEditText(context)
        text.setText("Hello WassUP?", TextView.BufferType.NORMAL)
        addView(text)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when ((event!!.action and MotionEvent.ACTION_MASK)) {
            MotionEvent.ACTION_DOWN -> {
                paint.color = Color.WHITE
                initX = event.x
                initY = event.y
                activePointerID = event.getPointerId(0)
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val pointerIndex: Int = event.findPointerIndex(activePointerID)
                translationX += (event.getX(pointerIndex) - initX)
                translationY += (event.getY(pointerIndex) - initY)
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex: Int =
                    (event.action and MotionEvent.ACTION_POINTER_INDEX_MASK) shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
                val pointerId: Int = event.getPointerId(pointerIndex)
                if (pointerId == activePointerID) {
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    initX = event.getX(newPointerIndex)
                    initY = event.getY(newPointerIndex)
                    activePointerID = event.getPointerId(newPointerIndex)
                }
            }
            MotionEvent.ACTION_UP -> {
                paint.color = Color.GREEN
                activePointerID = INVALID_POINTER_ID
                parent.requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_CANCEL -> activePointerID = INVALID_POINTER_ID

        }
        return true
    }

}