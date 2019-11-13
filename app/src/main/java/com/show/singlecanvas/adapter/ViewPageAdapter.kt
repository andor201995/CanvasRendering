package com.show.singlecanvas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.show.singlecanvas.R
import com.show.singlecanvas.customview.slideView.SlideViewMultipleCanvas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ViewPageAdapter(val context: Context) :
    RecyclerView.Adapter<ViewPageAdapter.ViewPageHolder>(), CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    var numOfItemsInViewPage = 1000

    private var renderState: RenderState = RenderState.Rendering.StartRendering

    private val map = HashMap<Int, Job>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPageHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.view_page_item, parent, false)
        return ViewPageHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewPageHolder, position: Int) {
        val slideViewMultipleCanvas = SlideViewMultipleCanvas(context)
        holder.slideViewItemHolder.addView(slideViewMultipleCanvas)
        holder.positionHolder = position
//        if (renderState is RenderState.Rendering) {
        val childJob = launch {
            slideViewMultipleCanvas.setNumOfObjects(numOfItemsInViewPage)
            slideViewMultipleCanvas.startJob()
        }
        map[position] = childJob
//        }
    }

    override fun onViewRecycled(holder: ViewPageHolder) {
        val childJob = map[holder.positionHolder]
        if (childJob is Job && childJob.isActive) {
            childJob.cancel()
            map.remove(holder.positionHolder)
        }
        super.onViewRecycled(holder)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        job.cancel()
    }

    fun setScrollType(
        renderState: RenderState
    ) {
        this.renderState = renderState
//        if (renderState is RenderState.Rendering.NotifyRendering) {
//            for (pos in renderState.fromPosition until renderState.toPosition + 1) {
//                notifyItemChanged(pos)
//            }
//        }
    }

    inner class ViewPageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val slideViewItemHolder: FrameLayout =
            view.findViewById(R.id.itemSlideViewMultipleCanvasHolder)
        var positionHolder: Int = -1
    }

    sealed class RenderState {
        sealed class Rendering : RenderState() {
            object StartRendering : Rendering()
            data class NotifyRendering(val fromPosition: Int, val toPosition: Int) : Rendering()
        }

        object CancelRendering : RenderState()
    }

}