package com.show.singlecanvas

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.show.singlecanvas.customview.SlideView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val thumbnailRecyclerView = thumbnail as RecyclerView
        val thumbnailAdapter = ThumbnailAdapter(applicationContext)
        thumbnailRecyclerView.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        thumbnailRecyclerView.layoutManager = linearLayoutManager
        thumbnailRecyclerView.adapter = thumbnailAdapter

        thumbnailAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        if (slideView is SlideView) {
            slideView.startSurfaceDrawThread()
        }
    }

    override fun onPause() {
        super.onPause()
        if (slideView is SlideView) {
            slideView.stopSurfaceDrawThread()
        }
    }
}
