package com.example.myapplication

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.view.View
import com.example.myapplication.databinding.ActivityAnimationBinding

class Animation : AppCompatActivity() {

    lateinit var binding: ActivityAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView15.setOnClickListener {
            goWithAnimation()
        }

    }

    fun goWithAnimation() {
        lateinit var bundle: Bundle


        val p1 = Pair.create<View, String>(
                binding.imageView15, "lalala"
        )

        val p2 = Pair.create<View, String>(
            binding.textView20, "lalala2"
        )


        val intent = Intent(this, Animation11::class.java)

        val options = ActivityOptions.makeSceneTransitionAnimation(this, p1, p2)
        bundle = options.toBundle()



        startActivity(intent, bundle)
    }
}