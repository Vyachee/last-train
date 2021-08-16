package com.example.myapplication

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.AnimationSet
import android.widget.FrameLayout
import com.example.myapplication.databinding.ActivityBottomAnimationBinding
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import android.view.animation.DecelerateInterpolator

import android.animation.FloatEvaluator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay


class BottomAnimation : AppCompatActivity() {

    lateinit var binding: ActivityBottomAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)



        GlobalScope.launch(Dispatchers.Main) {
            animateCard(binding.fl1)
            delay(100)
            animateCard(binding.fl2)
            delay(100)
            animateCard(binding.fl3)
            delay(100)
            animateCard(binding.fl4)

            delay(750)
            binding.frameLayout5.animate().alpha(1f).setDuration(500)

            delay(500)
            animateLine()

            delay(400)

            animateTexts()

        }



    }

    fun animateTexts() {
        val valueAnimator = ValueAnimator.ofInt(-100, 60)
        valueAnimator.duration = 350
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            val fraction = animation.animatedValue
            val params = binding.textView22.layoutParams as FrameLayout.LayoutParams
            params.setMargins(fraction as Int, 0, 0, 0)

            val params2 = binding.textView23.layoutParams as FrameLayout.LayoutParams
            params2.setMargins(0, 0, fraction as Int, 0)

            binding.textView22.layoutParams = params
            binding.textView23.layoutParams = params2
        }
        valueAnimator.repeatCount = 0
        valueAnimator.start()
    }

    fun animateLine() {
        val valueAnimator = ValueAnimator.ofInt(0, 703)
        valueAnimator.duration = 350
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            val fraction = animation.animatedValue
            val params = binding.flLine.layoutParams
            params.width = fraction as Int

            binding.flLine.layoutParams = params
            binding.flLine.requestLayout()
        }
        valueAnimator.repeatCount = 0
        valueAnimator.start()
    }

    fun animateCard(view: View) {
        val valueAnimator = ValueAnimator.ofInt(0, 263)
        valueAnimator.duration = 750
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            val fraction = animation.animatedValue
            val params = view.layoutParams
            params.height = fraction as Int

            view.layoutParams = params
            view.requestLayout()
        }
        valueAnimator.repeatCount = 0
        valueAnimator.start()
    }
}