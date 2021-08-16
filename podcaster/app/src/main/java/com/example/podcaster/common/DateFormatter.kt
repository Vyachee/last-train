package com.example.podcaster.common

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class DateFormatter {

    private val date = Date()

    fun getCurrentDateFormatted(): String {
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(date)
    }

    fun getCurrentTimeFormatted(): String {
        val format = SimpleDateFormat("HH:mm:ss")
        return format.format(date)
    }

}