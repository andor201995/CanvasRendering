package com.show.singlecanvas.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.show.singlecanvas.R
import com.show.singlecanvas.model.ListModel


class ListingAdapter(
    private val context: Context,
    private val contentList: HashMap<Int, ListModel>

) : RecyclerView.Adapter<ListingAdapter.ListingHolder>() {

    override fun getItemCount(): Int {
        return contentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.listing_item, parent, false)
        return ListingHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListingHolder, position: Int) {
        holder.txtView.text = contentList[position]!!.listName
        holder.itemHolder.setOnClickListener(holder)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ListingHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        override fun onClick(v: View?) {
            val listModel = contentList[adapterPosition]
            val intent = Intent(context, listModel!!.activityClass)
            context.startActivity(intent)
        }

        val txtView: TextView = view.findViewById(R.id.listingText)
        val itemHolder: View = view.findViewById(R.id.itemHolder)
    }
}