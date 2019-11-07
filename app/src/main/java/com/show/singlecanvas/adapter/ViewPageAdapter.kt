package com.show.singlecanvas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.show.singlecanvas.R
import com.show.singlecanvas.customview.SlideView.SlideViewMultipleCanvas

class ViewPageAdapter(val context: Context) :
    RecyclerView.Adapter<ViewPageAdapter.ViewPageHolder>() {

    var numOfItemsInViewPage = 1000

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPageHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.view_page_item, parent, false)

        return ViewPageHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: ViewPageHolder, position: Int) {
        holder.slideViewItem.setNumOfObjects(numOfItemsInViewPage)
    }

    inner class ViewPageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val slideViewItem =
            view.findViewById<SlideViewMultipleCanvas>(R.id.itemSlideViewMultipleCanvas)
    }
}