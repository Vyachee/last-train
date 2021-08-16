package com.example.podcaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.podcaster.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivProcceed.setOnClickListener {
            startActivity(Intent(this, MainScreen::class.java))
            finish()
        }

        GlobalScope.launch(Dispatchers.Main) {
            delay(500)
            startActivity(Intent(baseContext, MainScreen::class.java))
        }
    }
}