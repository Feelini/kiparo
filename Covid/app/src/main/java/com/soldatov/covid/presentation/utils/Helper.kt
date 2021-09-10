package com.soldatov.covid.presentation.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun dateFormat(timeMilli: Long): String {
    val simpleDateFormat = SimpleDateFormat("dd MMM, yyyy")
    return "last update " + simpleDateFormat.format(timeMilli)
}