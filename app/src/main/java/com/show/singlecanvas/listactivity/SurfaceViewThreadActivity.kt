package com.show.singlecanvas.listactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.show.singlecanvas.R
import com.show.singlecanvas.SingleCanvasApplication
import kotlinx.android.synthetic.main.activity_surface_view_thread.*

class SurfaceViewThreadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surface_view_thread)
    }

    override fun onStart() {
        super.onStart()
        slideViewSurfaceThread.setNumOfObjects((application as SingleCanvasApplication).getNumberOfObject())
    }

    override fun onResume() {
        super.onResume()
        slideViewSurfaceThread.startSurfaceDrawThread()
    }

    override fun onPause() {
        super.onPause()
        slideViewSurfaceThread.stopSurfaceDrawThread()
    }
}

