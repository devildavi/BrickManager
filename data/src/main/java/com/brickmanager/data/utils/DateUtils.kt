package com.brickmanager.data.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Returns the current date as a formatted string (e.g., "2023-10-27").
 */
fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date())
}
