package com.show.singlecanvas.listactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.show.singlecanvas.R
import com.show.singlecanvas.SingleCanvasApplication
import kotlinx.android.synthetic.main.activity_single_view_canvas.*

class SingleViewCanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_view_canvas)
    }

    override fun onStart() {
        super.onStart()
        slideViewSingleCanvas.setNumOfObjects((application as SingleCanvasApplication).getNumberOfObject())
    }
}
