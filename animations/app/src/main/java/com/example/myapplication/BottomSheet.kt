package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityBottomSheetBinding

class BottomSheet : AppCompatActivity() {

    lateinit var binding: ActivityBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = Adapter()
        val manager = LinearLayoutManager(baseContext)

        binding.isheet.rv.adapter = adapter
        binding.isheet.rv.layoutManager = manager

    }



}