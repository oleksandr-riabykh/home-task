package com.alex.scotiagit.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.toDisplayDate(): String =
    toDisplayDate(DATE_SIMPLE_FORMAT, DATE_SIMPLE_FORMAT_DISPLAY)

fun String.toDisplayDate(formatFromString: String, formatToString: String): String {
    val formatFrom = SimpleDateFormat(formatFromString, Locale.US).parse(this)
    val formatTo = SimpleDateFormat(formatToString, Locale.US)
    return formatFrom?.let { formatTo.format(it) } ?: "All"
}