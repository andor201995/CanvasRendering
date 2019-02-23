package com.show.singlecanvas.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.show.singlecanvas.R
import com.show.singlecanvas.customview.SlideView.SlideViewSurfaceThread


class ThumbnailAdapter(
    private val context: Context
) : RecyclerView.Adapter<ThumbnailAdapter.ThumbnailHolder>() {

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
//        holder.slideView.startSurfaceDrawThread()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onViewDetachedFromWindow(holder: ThumbnailHolder) {
//        holder.slideView.stopSurfaceDrawThread()
        super.onViewDetachedFromWindow(holder)
    }

    inner class ThumbnailHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtView: TextView = view.findViewById(R.id.itemTextView)
//        var slideView: SlideViewSurfaceThread = view.findViewById(R.id.itemSlideView)
    }
}