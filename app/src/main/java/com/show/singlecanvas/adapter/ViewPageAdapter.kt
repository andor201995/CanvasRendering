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

class ViewPageAdapter(val context: Context) :
    RecyclerView.Adapter<ViewPageAdapter.ViewPageHolder>() {

    private val job = Job()
    private val scopeMain = CoroutineScope(job + Dispatchers.Main)
    private val scopeIO = CoroutineScope(job + Dispatchers.IO)

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

    override fun onBindViewHolder(holder: ViewPageHolder, position: Int) {
        scopeIO.launch {
            val slideViewMultipleCanvas = SlideViewMultipleCanvas(context)
            slideViewMultipleCanvas.setNumOfObjects(numOfItemsInViewPage)
            scopeMain.launch {
                holder.slideViewItemHolder.addView(slideViewMultipleCanvas)
            }
        }
    }

    inner class ViewPageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val slideViewItemHolder: FrameLayout =
            view.findViewById(R.id.itemSlideViewMultipleCanvasHolder)
    }
}