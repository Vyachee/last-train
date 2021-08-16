package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class CustomToast : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_toast)

        val toast = Toast(applicationContext)
        val view = layoutInflater.inflate(R.layout.custom_toast, null)

        val params = ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        view.layoutParams = params

        toast.view = view
        toast.setGravity((Gravity.FILL_HORIZONTAL or Gravity.BOTTOM), 0, -590)
        toast.duration = Toast.LENGTH_LONG
        toast.show()

    }
}