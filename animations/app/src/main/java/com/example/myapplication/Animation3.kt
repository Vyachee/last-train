package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityAnimation3Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Animation3 : AppCompatActivity() {

    lateinit var binding: ActivityAnimation3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimation3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView14.setOnClickListener {
            animate()
        }

    }

    fun animate() {

        binding.flCircle.scaleX = 1f
        binding.flCircle.scaleY = 1f

        val circleScale = 5f
        binding.flCircle.animate().scaleX(circleScale).scaleY(circleScale).setDuration(2000)

        GlobalScope.launch(Dispatchers.Main) {
            delay(2000)
            binding.flCircle.scaleX = 0.2f
            binding.flCircle.scaleY = 0.2f
        }

        binding.flOverlay.animate().alpha(0f).setDuration(200)

        GlobalScope.launch(Dispatchers.Main) {
            delay(200)

            binding.flOverlay.scaleX = 0.25f
            binding.flOverlay.scaleY = 0.25f

            binding.flOverlay.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(2000)

        }


    }
}