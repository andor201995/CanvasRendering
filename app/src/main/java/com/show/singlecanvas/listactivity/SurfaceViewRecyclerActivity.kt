package com.show.singlecanvas.listactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.show.singlecanvas.R
import com.show.singlecanvas.SingleCanvasApplication
import com.show.singlecanvas.adapter.ThumbnailAdapter
import kotlinx.android.synthetic.main.activity_surface_view_recycler.*


class SurfaceViewRecyclerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_surface_view_recycler)
        val thumbnailRecyclerView = thumbnail as RecyclerView
        val thumbnailAdapter = ThumbnailAdapter(applicationContext)
        thumbnailRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this@SurfaceViewRecyclerActivity,
                LinearLayoutManager.HORIZONTAL
            )
        )
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        thumbnailRecyclerView.layoutManager = linearLayoutManager
        thumbnailRecyclerView.adapter = thumbnailAdapter
    }

    override fun onStart() {
        super.onStart()
        slideViewSurfaceRecycler.setNumOfObjects((application as SingleCanvasApplication).getNumberOfObject())
    }

    override fun onResume() {
        super.onResume()
//        slideViewSurfaceRecycler.startSurfaceDrawThread()
    }

    override fun onPause() {
        super.onPause()
//        slideViewSurfaceRecycler.stopSurfaceDrawThread()
    }
}
