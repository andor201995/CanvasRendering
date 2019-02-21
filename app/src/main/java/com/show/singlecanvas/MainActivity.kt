package com.show.singlecanvas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        if(slideView is SlideView){
            slideView.startSurfaceDrawThread()
        }
    }

    override fun onPause() {
        super.onPause()
        if(slideView is SlideView){
            slideView.stopSurfaceDrawThread()
        }
    }
}
