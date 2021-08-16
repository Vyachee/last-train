package com.example.workwithdata.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workwithdata.R
import com.example.workwithdata.common.ExportItem
import com.example.workwithdata.common.Plant
import com.example.workwithdata.databinding.PlantCardBinding

class PlantsAdapter(var list: List<Plant>,
                    val context: Context
                    ):
    RecyclerView.Adapter<PlantsAdapter.Holder>() {

    val stockList = list

    @SuppressLint("NotifyDataSetChanged")
    fun filter(export: MutableList<String>?, colors: MutableList<String>?, priceSort: String, available: String, date: String, child: String) {
        list = stockList
        if(
            (export == null || export.size == 0)
            &&
            (colors == null || colors.size == 0)
            && priceSort.isEmpty()
            && available.isEmpty()
        ) {
            notifyDataSetChanged()
            return
        }

        var filtered: List<Plant>

        if(export?.size != 0) {
            filtered = list.filter { export!!.contains(it.exportedFrom) }
            list = filtered
        }

        if(colors?.size != 0) {
            filtered =  list.filter { colors!!.contains(it.color) }
            list = filtered
        }

        if(priceSort == "lowToHigh") {
            filtered = list.sortedBy { it.priceNumeric }
            list = filtered
        }   else {
            filtered = list.sortedBy { it.priceNumeric }
            filtered = filtered.reversed()
            list = filtered
        }

        if(available == "all") {

        }   else if(available == "available") {
            filtered = list.filter { it.availability }
            list = filtered
        }   else {

            filtered = list.filter { !it.availability }
            list = filtered
        }

        if(date == "oldToNew") {
            filtered = list.sortedBy { it.realDate }
            list = filtered
        }   else {
            filtered = list.sortedBy { it.realDate }
            list = filtered.reversed()
        }


        if(child == "zeroToMany") {
            filtered = list.sortedBy { it.childCount }
            list = filtered
        }   else {
            filtered = list.sortedBy { it.childCount }
            list = filtered.reversed()
        }

        Log.e("DEBUG", "sort: $priceSort")

        notifyDataSetChanged()

    }


    class Holder(val binding: PlantCardBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = PlantCardBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val plant = list.get(position)
        holder.binding.tvPrice.text = plant.price
        holder.binding.tvProductName.text = plant.productName
        holder.binding.tvProductFamily.text = plant.exportedFrom
        holder.binding.tvCount.text = plant.childCount.toString()
        holder.binding.tvColor.text = plant.color


        if(plant.availability) {
            holder.binding.tvPrice.setTextColor(context.resources.getColor(R.color.green))
        }   else {
            holder.binding.tvPrice.setTextColor(context.resources.getColor(R.color.red))
        }

        Glide
            .with(context)
            .load(plant.image)
            .placeholder(R.drawable.loading_placeholder)
            .into(holder.binding.ivPoster)
    }

    override fun getItemCount(): Int = list.count()
}