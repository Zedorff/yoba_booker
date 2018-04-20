package com.zedorff.yobabooker.ui.databinding

import java.text.SimpleDateFormat
import java.util.*


fun convertUnixToString(timestamp: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return sdf.format(calendar.time)
}