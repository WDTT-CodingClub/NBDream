package kr.co.main.accountbook.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun String?.formatSendDateTime(): String {
    val inputFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())
    val outputFormat = SimpleDateFormat(DATE_TIME_SEND_FORMAT_PATTERN, Locale.getDefault())
    val date = if (this.isNullOrBlank()) Date() else inputFormat.parse(this)
    return outputFormat.format(date)
}

internal fun String?.formatReceiveDateTime(): String {
    val inputFormat = SimpleDateFormat(DATE_TIME_RECEIVE_FORMAT_PATTERN, Locale.getDefault())
    val outputFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())
    val date = if (this.isNullOrBlank()) Date() else inputFormat.parse(this)
    return outputFormat.format(date)
}


internal const val DATE_FORMAT_PATTERN = "yyyy.MM.dd"
internal const val DATE_TIME_RECEIVE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss"
internal const val DATE_TIME_SEND_FORMAT_PATTERN = "yyyy-MM-dd 00:00"
