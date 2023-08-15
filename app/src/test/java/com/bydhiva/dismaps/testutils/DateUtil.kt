package com.bydhiva.dismaps.testutils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.parseDate(): Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return formatter.parse(this) ?: Date()
}