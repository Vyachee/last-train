package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityAnimationHubBinding

class AnimationHub : AppCompatActivity() {

    lateinit var binding: ActivityAnimationHubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val first = Intent(this, Animation::class.java)         // new york
        val second = Intent(this, Animation2::class.java)       // small circle image to big image activity
        val third = Intent(this, BallAnimation::class.java)     // navigation circle
        val fourth = Intent(this, BottomAnimation::class.java)  // woman bg
        val fifth = Intent(this, Animation3::class.java)        // bt

        binding.btnFirst.setOnClickListener { startActivity(first) }
        binding.btnSecond.setOnClickListener { startActivity(second) }
        binding.btnThird.setOnClickListener { startActivity(third) }
        binding.btnFourth.setOnClickListener { startActivity(fourth) }
        binding.btnFifth.setOnClickListener { startActivity(fifth) }

    }
}