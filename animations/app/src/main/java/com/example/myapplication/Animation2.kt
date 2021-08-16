package com.example.myapplication

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.view.View
import com.example.myapplication.databinding.ActivityAnimation2Binding

class Animation2 : AppCompatActivity() {

    lateinit var binding: ActivityAnimation2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimation2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView18.setOnClickListener {
            goWithAnimation()
        }
    }


    fun goWithAnimation() {
        lateinit var bundle: Bundle


        val p1 = Pair.create<View, String>(
            binding.cvCardView, "la"
        )
//        val p2 = Pair.create<View, String>(
//            binding.imageView18, "la2",
//        )



        val intent = Intent(this, Animation44::class.java)

        val options = ActivityOptions.makeSceneTransitionAnimation(this, p1)
        bundle = options.toBundle()

        startActivity(intent, bundle)
    }
}