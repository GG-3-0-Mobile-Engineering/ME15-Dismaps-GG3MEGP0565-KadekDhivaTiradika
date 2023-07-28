package com.bydhiva.dismaps.utils

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun Date.toStringISO(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    return formatter.format(this)
}

fun Pair<Date, Date>.toShortText(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    return "${formatter.format(this.first)}-${formatter.format(this.second)}"
}