package com.soldatov.covid.presentation.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun dateFormat(date: Date): String {
    return "last update " + SimpleDateFormat("dd MMM, yyyy").format(date)
}