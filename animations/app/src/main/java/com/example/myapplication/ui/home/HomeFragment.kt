package com.example.myapplication.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.tvBottomSheet.setOnClickListener {
            context?.startActivity(Intent(context, BottomSheet::class.java))
        }

        binding.tvPopup.setOnClickListener {
            context?.startActivity(Intent(context, PositionedPopup::class.java))
        }

        binding.tvSnackbar.setOnClickListener {
            context?.startActivity(Intent(context, SnackBar::class.java))
        }

        binding.tvToast.setOnClickListener {
            context?.startActivity(Intent(context, CustomToast::class.java))
        }

        binding.tvDialog.setOnClickListener {
            context?.startActivity(Intent(context, CustomAlert::class.java))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}