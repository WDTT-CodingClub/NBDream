package kr.co.common.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.format(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return format(formatter)
}

fun LocalDate.format(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return format(formatter)
}

object DateFormatter {
    fun fromPattern(text: String, pattern: String): LocalDate =
        LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern))
}