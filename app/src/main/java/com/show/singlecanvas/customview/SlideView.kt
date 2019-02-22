package com.show.singlecanvas.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.FrameLayout
import com.show.singlecanvas.model.DrawableView
import com.show.singlecanvas.model.ViewType
import com.show.singlecanvas.talker.ITalkToSlideView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class SlideView : FrameLayout, ITalkToSlideView {
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
    private val selectedShapeList = ArrayList<Int>()
    private var shapeSurfaceView: ShapeSurfaceView? = null


    private val drawableList = HashMap<ViewType, TreeMap<Int, DrawableView>>()

    init {

        slidePaint.style = Paint.Style.FILL
        slidePaint.color = Color.GRAY
        setShapeObject()
        setBBoxShapeObject()
        setWillNotDraw(false)

        post {
            slideLeftTop = floatArrayOf((width - slideWidth) / 2, (height - slideHeight) / 2)
            invalidate()
            shapeSurfaceView = ShapeSurfaceView(context, this)
            val lp =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            shapeSurfaceView!!.layoutParams = lp
            addView(shapeSurfaceView)
        }
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

        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {


        when (event!!.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                initX = event.x
                initY = event.y

                val shapeIndex = getShapeIndex(initX, initY, drawableList[ViewType.ShapeRev]!!)
                if (shapeIndex != -1) {
                    selectedShapeList.add(shapeIndex)
                    val drawableView = drawableList[ViewType.Shape]!![shapeIndex]
                    if (drawableView is DrawableView.ShapeObject) {
                        val margin = 20
                        val viewBounds = RectF()
                        drawableView.path.computeBounds(viewBounds, true)
                        val dirtyRect: Rect = Rect()
                        dirtyRect.set(
                            (-margin + slideLeftTop[0] + drawableView.left).toInt(),
                            (-margin + slideLeftTop[1] + drawableView.top).toInt(),
                            (margin + slideLeftTop[0] + drawableView.left + viewBounds.width()).toInt(),
                            (margin + slideLeftTop[1] + drawableView.top + viewBounds.height()).toInt()
                        )
                        shapeSurfaceView!!.drawOnCanvas(dirtyRect)
                    }
                }

            }
            MotionEvent.ACTION_MOVE -> {
                val diffX = event.x - initX
                val diffY = event.y - initY
                moveSelectedBBox(diffX, diffY, drawableList[ViewType.ShapeBBox]!!)

                initX = event.x
                initY = event.y
            }
            MotionEvent.ACTION_UP -> {

                moveSelectedShape(drawableList[ViewType.Shape]!!, drawableList[ViewType.ShapeBBox]!!)
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
            selectedShapeList.clear()
            shapeSurfaceView!!.drawOnCanvas()
        }
    }

    private fun moveSelectedBBox(diffX: Float, diffY: Float, treeMap: TreeMap<Int, DrawableView>) {
        if (selectedShapeList.size > 0) {
            val dirtyRect = Rect()
            selectedShapeList.forEach {
                val drawableView = treeMap[it]
                if (drawableView is DrawableView.ShapeBBoxObject) {
                    val margin = 20
                    val viewBounds = RectF()
                    drawableView.path.computeBounds(viewBounds, true)
                    val tempRect = Rect()
                    tempRect.set(
                        (-margin + slideLeftTop[0] + drawableView.left).toInt(),
                        (-margin + slideLeftTop[1] + drawableView.top).toInt(),
                        (margin + slideLeftTop[0] + drawableView.left + viewBounds.width()).toInt(),
                        (margin + slideLeftTop[1] + drawableView.top + viewBounds.height()).toInt()
                    )
                    dirtyRect.union(tempRect)
                    drawableView.left += diffX
                    drawableView.top += diffY
                    tempRect.set(
                        (-margin + slideLeftTop[0] + drawableView.left).toInt(),
                        (-margin + slideLeftTop[1] + drawableView.top).toInt(),
                        (margin + slideLeftTop[0] + drawableView.left + viewBounds.width()).toInt(),
                        (margin + slideLeftTop[1] + drawableView.top + viewBounds.height()).toInt()
                    )
                    dirtyRect.union(tempRect)
                }
            }
            shapeSurfaceView!!.drawOnCanvas(dirtyRect)
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

    override fun getBBoxMap(): TreeMap<Int, DrawableView> {
        return drawableList[ViewType.ShapeBBox]!!
    }

    override fun getSelectedShapeList(): ArrayList<Int> {
        return selectedShapeList
    }

    override fun getSlideLeftTop(): FloatArray {
        return slideLeftTop
    }

    override fun getShapeMap(): TreeMap<Int, DrawableView> {
        return drawableList[ViewType.Shape]!!
    }

    fun startSurfaceDrawThread() {
        shapeSurfaceView?.startDrawThread()
    }

    fun stopSurfaceDrawThread() {
        shapeSurfaceView?.stopDrawThread()
    }
}
