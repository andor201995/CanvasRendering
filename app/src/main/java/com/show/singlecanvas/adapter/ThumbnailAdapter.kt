package com.show.singlecanvas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.show.singlecanvas.R
import com.show.singlecanvas.customview.SlideView.SlideViewSurface


class ThumbnailAdapter(
    private val context: Context
) : androidx.recyclerview.widget.RecyclerView.Adapter<ThumbnailAdapter.ThumbnailHolder>() {

    var numOfObjectsForThumbnail: Int = 1000

    override fun getItemCount(): Int {
        return 100
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.thumbnail_item, parent, false)
        return ThumbnailHolder(itemView)
    }

    override fun onBindViewHolder(holder: ThumbnailHolder, position: Int) {
        holder.txtView.text = "Slide: $position"
        holder.surfaseView.setNumOfObjects(numOfObjectsForThumbnail)
//        holder.slideView.startSurfaceDrawThread()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ThumbnailHolder(view: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        var txtView: TextView = view.findViewById(R.id.itemTextView)
        val surfaseView = view.findViewById<SlideViewSurface>(R.id.itemSlideView)
//        var slideView: SlideViewSurfaceThread = view.findViewById(R.id.itemSlideView)
    }
}