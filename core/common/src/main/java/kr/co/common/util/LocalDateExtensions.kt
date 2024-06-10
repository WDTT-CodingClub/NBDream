package kr.co.common.util

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
                //TODO string resource 사용하도록 바꾸기
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
    //TODO string resource 사용하도록 바꾸기
    .replace("AM", "오전")
    .replace("PM", "오후")

@RequiresApi(Build.VERSION_CODES.O)
operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> {
    return object : Iterator<LocalDate> {
        private var next = this@iterator.start
        private val finalElement = this@iterator.endInclusive
        private var hasNext = !next.isAfter(this@iterator.endInclusive)

        override fun hasNext(): Boolean = hasNext
        override fun next(): LocalDate {
            val value = next
            if (value == finalElement) {
                hasNext = false
            } else {
                next = next.plusDays(1)
            }
            return value
        }
    }
}
