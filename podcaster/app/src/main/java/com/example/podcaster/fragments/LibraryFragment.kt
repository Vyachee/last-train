package com.example.podcaster.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.podcaster.R
import com.example.podcaster.adapters.RecordsAdapter
import com.example.podcaster.common.FileSystemHelper
import com.example.podcaster.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {

    lateinit var binding: FragmentLibraryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibraryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = FileSystemHelper().getRecords(requireContext())

        val adapter = RecordsAdapter(list)
        val manager = LinearLayoutManager(context)
        binding.rvList4.adapter = adapter
        binding.rvList4.layoutManager = manager

    }

}