package com.show.singlecanvas.listactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.show.singlecanvas.R
import com.show.singlecanvas.SingleCanvasApplication
import kotlinx.android.synthetic.main.activity_surface_view.*

class SurfaceViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surface_view)
    }

    override fun onStart() {
        super.onStart()
        slideViewSurface.setNumOfObjects((application as SingleCanvasApplication).getNumberOfObject())
    }
}
