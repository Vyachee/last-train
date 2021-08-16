package com.example.myapplication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.marginEnd
import com.example.myapplication.databinding.ActivitySnackBarBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.ContentViewCallback
import com.google.android.material.snackbar.Snackbar

class SnackBar : AppCompatActivity() {

    lateinit var binding: ActivitySnackBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySnackBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.removeAllViews()

        getSnackbar(binding.root).show()

    }


    fun getSnackbar(coordinatorLayout: CoordinatorLayout): Snackbar {
        val snackbar = Snackbar.make(coordinatorLayout, "", 4000)


        snackbar.view.elevation = 0f
        snackbar.view.setBackgroundColor(getColor(R.color.white))
        snackbar.view.background = getDrawable(R.drawable.round_white)

        val snackView = layoutInflater.inflate(R.layout.custom_snackbar, null)

        val snackBarView = snackbar.view as Snackbar.SnackbarLayout
        val params = snackBarView.layoutParams as CoordinatorLayout.LayoutParams

        params.setMargins(100, 100, 100, 100)

        snackBarView.layoutParams = params


        snackBarView.addView(snackView)
        return snackbar


    }

    class CustomSnackBarView(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ): ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {
        init {
            View.inflate(context, R.layout.custom_snackbar, this)
        }

        override fun animateContentIn(delay: Int, duration: Int) {
            val animatorSet = AnimatorSet().apply {
                interpolator = OvershootInterpolator()
                setDuration(500)
            }
            animatorSet.start()
        }

        override fun animateContentOut(delay: Int, duration: Int) {
            TODO("Not yet implemented")
        }
    }

    class CustomSnackBar(
        parent: ViewGroup,
        content: CustomSnackBarView
    ): BaseTransientBottomBar<CustomSnackBar>(parent, content, content)

}