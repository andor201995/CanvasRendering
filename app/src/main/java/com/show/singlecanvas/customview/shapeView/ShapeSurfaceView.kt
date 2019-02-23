package com.show.singlecanvas.customview.shapeView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.show.singlecanvas.model.DrawableView
import com.show.singlecanvas.talker.ITalkToSlideView
import java.util.*

class ShapeSurfaceView(context: Context, private val iTalkToSlideView: ITalkToSlideView) : SurfaceView(context),
    SurfaceHolder.Callback {


    private var screenWidth = 0

    private var screenHeight = 0

    private var surfaceHolder: SurfaceHolder? = null

    init {
        holder.addCallback(this)
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        // and release the surface
        surfaceHolder?.surface?.release()
        this.surfaceHolder = null
    }


    override fun surfaceCreated(holder: SurfaceHolder?) {
        screenHeight = height
        screenWidth = width
        drawOnCanvas()

    }

    fun drawOnCanvas(dirtyRect: Rect = Rect(0, 0, right, bottom)) {
        val canvas: Canvas? = holder.lockCanvas(dirtyRect)
        if (canvas != null) {
            val margin = 0

            val right = screenWidth - margin

            val bottom = screenHeight - margin

            val rect = Rect(margin, margin, right, bottom)

            // Draw the specify canvas background color. Clear the old canvas
            val backgroundPaint = Paint()
            backgroundPaint.color = Color.WHITE
            canvas.drawRect(rect, backgroundPaint)

            drawShape(canvas, iTalkToSlideView.getShapeMap())

            drawBBox(canvas, iTalkToSlideView.getBBoxMap())

            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun drawShape(canvas: Canvas, treeMap: TreeMap<Int, DrawableView>) {
        treeMap.forEach { (_, shapeDrawableView) ->
            if (shapeDrawableView is DrawableView.ShapeObject) {
                canvas.save()
                canvas.translate(
                    shapeDrawableView.left + iTalkToSlideView.getSlideLeftTop()[0],
                    shapeDrawableView.top + iTalkToSlideView.getSlideLeftTop()[1]
                )
                canvas.drawPath(shapeDrawableView.path, shapeDrawableView.paint)
                canvas.restore()
            }
        }
    }

    private fun drawBBox(canvas: Canvas, treeMap: TreeMap<Int, DrawableView>) {
        if (iTalkToSlideView.getSelectedShapeList().size > 0) {
            iTalkToSlideView.getSelectedShapeList().forEach {
                val drawableView = treeMap[it]
                if (drawableView is DrawableView.ShapeBBoxObject) {
                    canvas.save()
                    canvas.translate(
                        drawableView.left + iTalkToSlideView.getSlideLeftTop()[0],
                        drawableView.top + iTalkToSlideView.getSlideLeftTop()[1]
                    )
                    canvas.drawPath(drawableView.path, drawableView.paint)
                    canvas.restore()
                }
            }
        }
    }


}