package com.show.singlecanvas.listactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.show.singlecanvas.R
import com.show.singlecanvas.SingleCanvasApplication
import com.show.singlecanvas.adapter.ThumbnailAdapter
import kotlinx.android.synthetic.main.activity_surface_view_recycler.*


class SurfaceViewRecyclerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_surface_view_recycler)
        val thumbnailRecyclerView = thumbnail as androidx.recyclerview.widget.RecyclerView
        val thumbnailAdapter = ThumbnailAdapter(applicationContext)
        thumbnailRecyclerView.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                this@SurfaceViewRecyclerActivity,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
            )
        )
        val linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        linearLayoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
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
