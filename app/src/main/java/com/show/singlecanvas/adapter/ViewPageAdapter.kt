package com.show.singlecanvas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.show.singlecanvas.R
import com.show.singlecanvas.customview.slideView.SlideViewMultipleCanvas
import kotlinx.coroutines.*

class ViewPageAdapter(val context: Context) :
    RecyclerView.Adapter<ViewPageAdapter.ViewPageHolder>() {

    private val dispatcherIO = Dispatchers.IO
    private val job = Job()
    private val scopeIO = CoroutineScope(dispatcherIO + job)
    private val jobMap = HashMap<Int, Deferred<Boolean>>()

    var numOfItemsInViewPage = 1000

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
        slideViewMultipleCanvas.setNumOfObjects(numOfItemsInViewPage)
        holder.slideViewItemHolder.addView(slideViewMultipleCanvas)
        val deferred = scopeIO.async {
            slideViewMultipleCanvas.startJob(dispatcherIO)
            true
        }
        jobMap[position] = deferred
        holder.positionHolder = position
    }

    override fun onViewRecycled(holder: ViewPageHolder) {
        super.onViewRecycled(holder)
        jobMap[holder.positionHolder]!!.cancel()
    }

    inner class ViewPageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val slideViewItemHolder: FrameLayout =
            view.findViewById(R.id.itemSlideViewMultipleCanvasHolder)
        var positionHolder: Int = -1
    }

}