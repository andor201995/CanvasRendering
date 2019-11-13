package com.show.singlecanvas.listactivity

import android.content.ComponentCallbacks2
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.show.singlecanvas.R
import com.show.singlecanvas.SingleCanvasApplication
import com.show.singlecanvas.adapter.ViewPageAdapter
import kotlinx.android.synthetic.main.activity_surface_view_recycler.*


class SurfaceViewRecyclerActivity : AppCompatActivity(), ComponentCallbacks2 {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_surface_view_recycler)
        val thumbnailRecyclerView = thumbnail as RecyclerView
        val thumbnailAdapter = ViewPageAdapter(this)
        thumbnailAdapter.numOfItemsInViewPage =
            (application as SingleCanvasApplication).getNumberOfObject()
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
        thumbnailRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val renderState = when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        ViewPageAdapter.RenderState.Rendering.StartRendering
                    }
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        ViewPageAdapter.RenderState.Rendering.NotifyRendering(
                            linearLayoutManager.findFirstVisibleItemPosition(),
                            linearLayoutManager.findLastVisibleItemPosition()
                        )
                    }
                    else -> {
                        ViewPageAdapter.RenderState.CancelRendering
                    }
                }

                thumbnailAdapter.setScrollType(renderState)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        slideViewSurfaceRecycler.setNumOfObjects((application as SingleCanvasApplication).getNumberOfObject())
    }

    override fun onTrimMemory(level: Int) {
        when (level) {
            ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN -> {
                /*
                   Release any UI objects that currently hold memory.

                   The user interface has moved to the background.

                */
                System.gc()

            }

            ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL -> {
                /*
                   Release any memory that your app doesn't need to run.

                   The device is running low on memory while the app is running.
                   The event raised indicates the severity of the memory-related event.
                   If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
                   begin killing background processes.
                */
                System.gc()
            }

            ComponentCallbacks2.TRIM_MEMORY_BACKGROUND,
            ComponentCallbacks2.TRIM_MEMORY_MODERATE,
            ComponentCallbacks2.TRIM_MEMORY_COMPLETE -> {
                /*
                   Release as much memory as the process can.

                   The app is on the LRU list and the system is running low on memory.
                   The event raised indicates where the app sits within the LRU list.
                   If the event is TRIM_MEMORY_COMPLETE, the process will be one of
                   the first to be terminated.
                */
            }

            else -> {
                /*
                  Release any non-critical data structures.

                  The app received an unrecognized memory level value
                  from the system. Treat this as a generic low-memory message.
                */
            }
        }
        super.onTrimMemory(level)
    }
}
