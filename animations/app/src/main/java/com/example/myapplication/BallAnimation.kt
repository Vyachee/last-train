package com.example.myapplication

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.view.*
import com.example.myapplication.databinding.ActivityBallAnimationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BallAnimation : AppCompatActivity() {

    lateinit var binding: ActivityBallAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBallAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch(Dispatchers.Main) {

            delay(500)
            animate(100, 150, Direction.BOTTOM, 150)
            delay(150)
            animate(150, -100, Direction.BOTTOM, 300)
            delay(300)
            animate(100, 450, Direction.LEFT, 300)
            delay(300)
            animate(-100, 200, Direction.BOTTOM, 300)
            delay(600)
            animate(200, 75, Direction.BOTTOM, 150)
            delay(150)

            // little jump
            animate(75, 110, Direction.BOTTOM, 100)
            delay(100)
            animate(110, 100, Direction.BOTTOM, 100)
            delay(100)


            delay(500)

            animate(100, -100, Direction.BOTTOM, 300)
            delay(300)
            animate(450, 750, Direction.LEFT, 300)
            delay(300)
            animate(-100, 200, Direction.BOTTOM, 300)
            delay(600)
            animate(200, 75, Direction.BOTTOM, 150)
            delay(150)

            // little jump
            animate(75, 110, Direction.BOTTOM, 100)
            delay(100)
            animate(110, 100, Direction.BOTTOM, 100)
            delay(100)
        }

    }

    fun animate(start: Int, end: Int, direction: Direction, duration: Long) {
        val animator = ValueAnimator.ofInt(start, end)

        animator.duration = duration

        animator.addUpdateListener {
            val animatedValue = animator.animatedValue as Int
            val params = binding.ball.layoutParams as FrameLayout.LayoutParams

            val ball = binding.ball

            when(direction) {
                Direction.BOTTOM -> params.setMargins(ball.marginStart, ball.marginTop, ball.marginEnd, animatedValue)
                Direction.RIGHT -> params.setMargins(ball.marginStart, ball.marginTop, animatedValue, ball.marginBottom)
                Direction.TOP -> params.setMargins(ball.marginStart, animatedValue, ball.marginEnd, ball.marginBottom)
                Direction.LEFT -> params.setMargins(animatedValue, ball.marginTop, ball.marginEnd, ball.marginBottom)
            }


            binding.ball.layoutParams = params
        }

        animator.start()
    }

    enum class Direction {
        LEFT, TOP, RIGHT, BOTTOM
    }
}