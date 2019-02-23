package com.show.singlecanvas

import android.app.Application

class SingleCanvasApplication : Application() {

    private var numberOfObjects = 1000

    init {
        numberOfObjects = 1000
    }

    fun getNumberOfObject(): Int {
        return numberOfObjects
    }

    fun setNumberOfObject(numOfObject: Int) {
        this.numberOfObjects = numOfObject
    }

}