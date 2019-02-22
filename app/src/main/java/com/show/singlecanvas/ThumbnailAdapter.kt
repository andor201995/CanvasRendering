package com.show.singlecanvas

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


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
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ThumbnailHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtView: TextView = view.findViewById(R.id.itemTextView)
    }
}