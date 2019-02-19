package com.show.singlecanvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class SlideView : View {

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context) : super(context)

    private lateinit var slideLeftTop: FloatArray
    private val slideWidth =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300f, context.resources.displayMetrics)
    private val slideHeight =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, context.resources.displayMetrics)
    private val slidePaint = Paint()
    private val pathRegion = Region()
    private val pathBoundRect = RectF()
    private val numberOfShapes = 1000
    private var initX: Float = 0.0f
    private var initY: Float = 0.0f


    sealed class ViewType {
        object Shape : ViewType()
        object ShapeBBox : ViewType()
        object ShapeRev : ViewType()
    }

    private val selectedShapeList = ArrayList<Int>()


    private val drawableList = HashMap<ViewType, TreeMap<Int, DrawableView>>()

    init {
        post {
            slideLeftTop = floatArrayOf((width - slideWidth) / 2, (height - slideHeight) / 2)
        }
        slidePaint.style = Paint.Style.FILL
        slidePaint.color = Color.GRAY
        setShapeObject()
        setBBoxShapeObject()

        /* postDelayed({
             val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
             valueAnimator.duration = 1000
             valueAnimator.addUpdateListener { animation ->
                 Log.e("dur", animation!!.animatedValue.toString());
                 invalidate();
             }
             valueAnimator.start()

         }, 3000)*/
    }

    private fun setBBoxShapeObject() {
        val bboxPaint = Paint()
        bboxPaint.style = Paint.Style.STROKE
        bboxPaint.strokeWidth = 10f
        bboxPaint.color = Color.BLUE

        val treeMap = TreeMap<Int, DrawableView>()

        // adding  bbox
        val shapeTreeMap = drawableList[ViewType.Shape]

        shapeTreeMap!!.forEach { (id, shapeDrawableView) ->
            if (shapeDrawableView is DrawableView.ShapeObject) {
                treeMap[id] = DrawableView.ShapeBBoxObject(shapeDrawableView, bboxPaint)
            }
        }

        drawableList[ViewType.ShapeBBox] = treeMap

    }

    private fun setShapeObject() {

        val treeMap = TreeMap<Int, DrawableView>()

        val rand = Random()

        val randDim = floatArrayOf(
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, context.resources.displayMetrics),
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60f, context.resources.displayMetrics),
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70f, context.resources.displayMetrics),
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, context.resources.displayMetrics),
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90f, context.resources.displayMetrics),
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, context.resources.displayMetrics),
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110f, context.resources.displayMetrics),
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120f, context.resources.displayMetrics)
        )

        val randLeftTop = floatArrayOf(
            -50f, 50f,
            -60f, 60f,
            -70f, 70f,
            -80f, 80f,
            -90f, 90f,
            -100f, 100f
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

        // adding N shapes
        for (index in 0..numberOfShapes) {
            val randWidth = randDim[rand.nextInt(8)]
            val randHeight = randDim[rand.nextInt(8)]
            val shapePaint = Paint()
            shapePaint.style = Paint.Style.FILL
            shapePaint.color = randColor[rand.nextInt(8)]
            val randLeft = randLeftTop[rand.nextInt(12)]
            val randTop = randLeftTop[rand.nextInt(12)]
            treeMap[index] = DrawableView.ShapeObject(
                left = randLeft,
                top = randTop,
                paint = shapePaint,
                path = getRectPath(shapeWidth = randWidth, shapeHeight = randHeight)
            )
        }

        drawableList[ViewType.Shape] = treeMap

        val reverseTreeMap = TreeMap<Int, DrawableView>(Collections.reverseOrder())
        reverseTreeMap.putAll(treeMap)
        drawableList[ViewType.ShapeRev] = reverseTreeMap

    }

    private fun getRectPath(shapeLeft: Float = 0f, shapeTop: Float = 0f, shapeWidth: Float, shapeHeight: Float): Path {
        val path = Path()
        path.moveTo(shapeLeft, shapeTop)
        path.lineTo(shapeWidth, shapeTop)
        path.lineTo(shapeWidth, shapeHeight)
        path.lineTo(shapeLeft, shapeHeight)
        path.close()
        return path
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (!(::slideLeftTop.isInitialized)) {
            slideLeftTop = floatArrayOf((width - slideWidth) / 2, (height - slideHeight) / 2)
        }

        canvas!!.save()

        //translate to the center of screen
        canvas.translate(slideLeftTop[0], slideLeftTop[1])
        //Draw Slide
        canvas.drawRect(0f, 0f, slideWidth, slideHeight, slidePaint)

            drawShape(canvas, drawableList[ViewType.Shape]!!)
            drawBBox(canvas, drawableList[ViewType.ShapeBBox]!!)

        canvas.restore()
    }

    private fun drawBBox(canvas: Canvas, treeMap: TreeMap<Int, DrawableView>) {
        if (selectedShapeList.size > 0) {
            selectedShapeList.forEach {
                val drawableView = treeMap[it]
                if (drawableView is DrawableView.ShapeBBoxObject) {
                    canvas.save()
                    canvas.translate(drawableView.left, drawableView.top)
                    canvas.drawPath(drawableView.path, drawableView.paint)
                    canvas.restore()
                }
            }
        }
    }

    private fun drawShape(canvas: Canvas, treeMap: TreeMap<Int, DrawableView>) {
        treeMap.forEach { (_, shapeDrawableView) ->
            if (shapeDrawableView is DrawableView.ShapeObject) {
                canvas.save()
                canvas.translate(shapeDrawableView.left, shapeDrawableView.top)
                canvas.drawPath(shapeDrawableView.path, shapeDrawableView.paint)
                canvas.restore()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event!!.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                initX = event.x
                initY = event.y

                Runnable {
                    val shapeIndex = getShapeIndex(initX, initY, drawableList[ViewType.ShapeRev]!!)
                    if (shapeIndex != -1) {
                        selectedShapeList.add(shapeIndex)
                        postInvalidate()
                    }
                }.run()


            }
            MotionEvent.ACTION_MOVE -> {
                val diffX = event.x - initX
                val diffY = event.y - initY
                Runnable {
                    moveSelectedBBox(diffX, diffY, drawableList[ViewType.ShapeBBox]!!)
                }.run()

                initX = event.x
                initY = event.y
            }
            MotionEvent.ACTION_UP -> {
                Runnable {
                    moveSelectedShape(drawableList[ViewType.Shape]!!, drawableList[ViewType.ShapeBBox]!!)
                }.run()

                selectedShapeList.clear()
            }

        }
        return true
    }

    private fun moveSelectedShape(shapeMap: TreeMap<Int, DrawableView>, bboxMap: TreeMap<Int, DrawableView>) {
        if (selectedShapeList.size > 0) {
            selectedShapeList.forEach {
                val shapeDrawableView = shapeMap[it]
                val bboxDrawableView = bboxMap[it]
                if (shapeDrawableView is DrawableView.ShapeObject && bboxDrawableView is DrawableView.ShapeBBoxObject) {
                    shapeDrawableView.left = bboxDrawableView.left
                    shapeDrawableView.top = bboxDrawableView.top
                }
            }
            postInvalidate()
        }
    }

    private fun moveSelectedBBox(diffX: Float, diffY: Float, treeMap: TreeMap<Int, DrawableView>) {
        if (selectedShapeList.size > 0) {
            selectedShapeList.forEach {
                val drawableView = treeMap[it]
                if (drawableView is DrawableView.ShapeBBoxObject) {
                    drawableView.left += diffX
                    drawableView.top += diffY
                }
            }
            postInvalidate()
        }
    }

    private fun getShapeIndex(initX: Float, initY: Float, treeMap: TreeMap<Int, DrawableView>): Int {
        val relX = initX - slideLeftTop[0]
        val relY = initY - slideLeftTop[1]
        treeMap.forEach { (id, drawableView) ->
            if (drawableView is DrawableView.ShapeObject) {
                val x = relX - drawableView.left
                val y = relY - drawableView.top
                drawableView.path.computeBounds(pathBoundRect, true)
                pathRegion.setPath(
                    drawableView.path,
                    Region(
                        pathBoundRect.left.toInt(),
                        pathBoundRect.top.toInt(),
                        pathBoundRect.right.toInt(),
                        pathBoundRect.bottom.toInt()
                    )
                )
                if (pathRegion.contains(x.toInt(), y.toInt())) {
                    return id
                }
            }
        }

        return -1
    }
}