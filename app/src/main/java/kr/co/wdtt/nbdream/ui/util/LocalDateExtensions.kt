package kr.co.wdtt.nbdream.ui.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toTitleDateString() =
    this.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) +
            " " +
            when (this.dayOfWeek) {
                DayOfWeek.SUNDAY -> "일"
                DayOfWeek.MONDAY -> "월"
                DayOfWeek.TUESDAY -> "화"
                DayOfWeek.WEDNESDAY -> "수"
                DayOfWeek.THURSDAY -> "목"
                DayOfWeek.FRIDAY -> "금"
                DayOfWeek.SATURDAY -> "토"
            }

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDateString() = this.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toDateTimeString() = this
    .format(DateTimeFormatter.ofPattern("yyyy.MM.dd a HH:mm"))
    .replace("AM", "오전")
    .replace("PM", "오후")