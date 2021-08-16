package com.example.myapplication

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityAnimation11Binding

class Animation11 : AppCompatActivity() {

    lateinit var binding: ActivityAnimation11Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimation11Binding.inflate(layoutInflater)
        setContentView(binding.root)

//        val animator = ValueAnimator.ofFloat(binding.textView21.textSize,
//            (binding.textView21.textSize * 1.05).toFloat()
//        )
//        animator.setDuration(500)
//
//        animator.addUpdateListener {
//            val animatedValue = animator.animatedValue
//            binding.textView21.setTextSize(animatedValue as Float)
//        }
//
//        animator.start()

    }
}