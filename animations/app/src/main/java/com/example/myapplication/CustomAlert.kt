package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CustomAlert : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_alert)

        MyDialogFragment()
            .newInstance()

        MyDialogFragment()
            .newInstance()
            .show(supportFragmentManager, "tag")

    }
}