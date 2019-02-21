package com.show.singlecanvas

import android.graphics.Paint
import android.graphics.Path


sealed class DrawableView {

    data class ShapeObject(
            val paint: Paint = Paint(),
            var left: Float = 0f,
            var top: Float = 0f,
            val path: Path = Path()
    ) : DrawableView()

    data class ShapeBBoxObject(
            val paint: Paint = Paint(),
            var left: Float = 0f,
            var top: Float = 0f,
            val path: Path = Path()
    ) : DrawableView() {
        constructor(shapeObject: ShapeObject, paint: Paint) : this(paint, shapeObject.left, shapeObject.top, shapeObject.path)
    }
}

sealed class ViewType {
    object Shape : ViewType()
    object ShapeBBox : ViewType()
    object ShapeRev : ViewType()
}

