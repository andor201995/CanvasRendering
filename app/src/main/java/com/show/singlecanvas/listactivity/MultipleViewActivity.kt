package com.show.singlecanvas.listactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.show.singlecanvas.R
import com.show.singlecanvas.SingleCanvasApplication
import kotlinx.android.synthetic.main.activity_multiple_view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MultipleViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_view)
    }

    override fun onStart() {
        super.onStart()
        val singleCanvasApplication = application as SingleCanvasApplication
        GlobalScope.launch {
            slideViewMultipleCanvas.setNumOfObjects(singleCanvasApplication.getNumberOfObject())
            slideViewMultipleCanvas.startJob()
        }
    }
}
