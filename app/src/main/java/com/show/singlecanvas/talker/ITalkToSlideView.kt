package com.show.singlecanvas.talker

import com.show.singlecanvas.DrawableView
import java.util.*

interface ITalkToSlideView {
    fun getBBoxMap(): TreeMap<Int, DrawableView>
    fun getSelectedShapeList(): ArrayList<Int>
    fun getSlideLeftTop(): FloatArray
    fun getShapeMap(): TreeMap<Int, DrawableView>
}