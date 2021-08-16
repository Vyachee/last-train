package com.example.workwithdata.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workwithdata.R
import com.example.workwithdata.common.ExportItem
import com.example.workwithdata.common.Plant
import com.example.workwithdata.databinding.FilterExportCountryBinding
import com.example.workwithdata.databinding.PlantCardBinding

class CountriesAdapter(
    val list: List<ExportItem>,
    val context: Context,
    val callback: OnFilterChangeCallback
    ):
    RecyclerView.Adapter<CountriesAdapter.Holder>() {



    class Holder(val binding: FilterExportCountryBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FilterExportCountryBinding.inflate(LayoutInflater.from(parent.context))

        val params = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        binding.root.layoutParams = params

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = list[position]
        holder.binding.tvTitle.text = item.title
        holder.binding.checkBox.isChecked = item.checked

        holder.binding.checkBox.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1) {
                    // added
                    callback.onAdd(item.title)
                    Log.e("DEBUG", "item: ${item.title}")

                }   else {
                    //removed
                    callback.onRemove(item.title)
                    Log.e("DEBUG", "item: ${item.title}")
                }
            }

        })

    }

    override fun getItemCount(): Int = list.count()

}

interface OnFilterChangeCallback {
    fun onAdd(s: String)
    fun onRemove(s: String)
}