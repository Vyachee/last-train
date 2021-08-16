package com.example.podcaster.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.podcaster.R
import com.example.podcaster.common.RecordItem

class RecordsAdapter(val list: List<RecordItem>): RecyclerView.Adapter<RecordsAdapter.Holder>() {

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById<TextView>(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.track_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = list.get(position)
        holder.tvTitle.text = item.title
        holder.tvDescription.text = "Hey, it's your record! Be careful..."
    }

    override fun getItemCount(): Int {
        return list.count()
    }

}