package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.view.marginLeft
import com.example.myapplication.databinding.ActivityPositionedPopupBinding
import com.example.myapplication.databinding.PopupBinding

class PositionedPopup : AppCompatActivity() {

    lateinit var binding: ActivityPositionedPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPositionedPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showPopup(view: View) {

        val inflater = LayoutInflater.from(baseContext)

        val popup = PopupBinding.inflate(inflater)

        popup.arrow.x = view.x - 25
        popup.root.foregroundGravity = Gravity.BOTTOM

        binding.flContainer.addView(popup.root)

        Log.e("DEBUG", "view position: ${view.x}")

    }
}