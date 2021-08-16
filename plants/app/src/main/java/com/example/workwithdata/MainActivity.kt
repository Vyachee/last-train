package com.example.workwithdata

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workwithdata.adapters.*
import com.example.workwithdata.common.ColorItem
import com.example.workwithdata.common.ExportItem
import com.example.workwithdata.common.Plant
import com.example.workwithdata.common.PlantsResponse
import com.example.workwithdata.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import org.json.JSONArray
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var plants: PlantsResponse
    lateinit var binding: ActivityMainBinding

    private lateinit var filterExportedFrom: MutableList<String>
    lateinit var filterColors: MutableList<String>


    lateinit var filterExportedFromList: MutableList<ExportItem>
    lateinit var filterColorsList: MutableList<ColorItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val json = readFile()
        initPlants(json)
        initList()

        initListeners()
    }

    fun initListeners() {
        val behavior = BottomSheetBehavior.from(binding.includeFilter.root)
        binding.ivFilterBtn.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.includeFilter.tvFilter.setOnClickListener {

            var priceSort = ""

            when(binding.includeFilter.rgPrice.checkedRadioButtonId) {
                R.id.highToLow -> priceSort = "highToLow"
                R.id.lowToHigh -> priceSort = "lowToHigh"
            }


            var available = ""

            when(binding.includeFilter.rgAvailable.checkedRadioButtonId) {
                R.id.all -> available = "all"
                R.id.available -> available = "available"
                R.id.notAvailable -> available = "notAvailable"
            }


            var date = ""

            when(binding.includeFilter.rgDate.checkedRadioButtonId) {
                R.id.oldToNew -> date = "oldToNew"
                R.id.newToOld -> date = "newToOld"
            }

            var child = ""

            when(binding.includeFilter.rgChild.checkedRadioButtonId) {
                R.id.zeroToMany -> child = "zeroToMany"
                R.id.manyToZero -> child = "manyToZero"
            }

            (binding.rvList.adapter as PlantsAdapter).filter(filterExportedFrom, filterColors, priceSort, available, date, child)
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }

    private fun initExportFilter() {
        filterExportedFrom = mutableListOf()
        val adapter = CountriesAdapter(filterExportedFromList, this, object: OnFilterChangeCallback {
            override fun onAdd(s: String) {
                filterExportedFrom.add(s)
            }

            override fun onRemove(s: String) {
                filterExportedFrom.remove(s)
            }

        })
        val manager = LinearLayoutManager(this)

        binding.includeFilter.rvExport.adapter = adapter
        binding.includeFilter.rvExport.layoutManager = manager
    }

    private fun initColorsFilter() {
        filterColors = mutableListOf()
        val adapter = ColorsAdapter(filterColorsList, this, object: OnFilterColorChangeCallback {
            override fun onAdd(s: String) {
                filterColors.add(s)
            }

            override fun onRemove(s: String) {
                filterColors.remove(s)
            }

        })
        val manager = LinearLayoutManager(this)

        binding.includeFilter.rvColors.adapter = adapter
        binding.includeFilter.rvColors.layoutManager = manager
    }

    private fun getExportedFrom() {
        filterExportedFrom = mutableListOf()
        filterExportedFromList = mutableListOf()

        for(plant in plants.plants) {
            val exportedFrom = plant.exportedFrom
            if(!filterExportedFrom.contains(exportedFrom)) {
                filterExportedFrom.add(exportedFrom)
            }
        }

        for(i in filterExportedFrom) {
            filterExportedFromList.add(ExportItem(
                i, false
            ))
        }

        initExportFilter()

    }

    private fun getColors() {
        filterColors = mutableListOf()

        for(plant in plants.plants) {
            val color = plant.color
            if(!filterColors.contains(color)) {
                filterColors.add(color)
            }
        }

        filterColorsList = mutableListOf()
        for(color in filterColors) {
            filterColorsList.add(ColorItem(color, false))
            Log.e("DEBUG", "newColor: $color")
        }

        initColorsFilter()
    }

    private fun initList() {
        val adapter = PlantsAdapter(plants.plants, this)
        val manager = GridLayoutManager(this, 2)

        binding.rvList.adapter = adapter
        binding.rvList.layoutManager = manager

        binding.rvList.addItemDecoration(CardMargins(this))
    }

    private fun initPlants(json: String?) {

        try {
            val jsonArray = JSONArray(json)
            plants = PlantsResponse(mutableListOf())

            for(i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val plant = Gson().fromJson(jsonObject.toString(), Plant::class.java)

                val price = plant.price.replace("$", "")
                val realPrice = price.toDouble()

                val dateFormatter = SimpleDateFormat("MM/dd/yyyy")
                val date = dateFormatter.parse(plant.exportDate)

                val childCount = plant.descendants.count()

                plant.priceNumeric = realPrice
                plant.realDate = date?.time
                plant.childCount = childCount

                plants.plants.add(plant)
            }

            getExportedFrom()
            getColors()

        }   catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Oops", Toast.LENGTH_SHORT).show()
        }

    }

    private fun readFile(): String? {
        val assetsManager = assets
        val inputStream = assetsManager.open("Plants.json")
        val scanner = Scanner(inputStream)
        scanner.useDelimiter("\\A")
        return scanner.next()
    }

    class CardMargins(private val context: Context): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            val index = parent.getChildAdapterPosition(view) + 1

            if(index % 2 == 0) {
                outRect.left = context.resources.getDimensionPixelOffset(R.dimen.cardMarginBetween)
                outRect.right = context.resources.getDimensionPixelOffset(R.dimen.cardMarginRight)
            }   else {
                outRect.left = context.resources.getDimensionPixelOffset(R.dimen.cardMarginLeft)
                outRect.right = context.resources.getDimensionPixelOffset(R.dimen.cardMarginBetween)
            }

            outRect.top = context.resources.getDimensionPixelOffset(R.dimen.cardMarginBottom)


            if(index == parent.childCount || index == parent.childCount - 1) {
                outRect.bottom = context.resources.getDimensionPixelOffset(R.dimen.cardMarginBottom)
            }

        }
    }
}