package kr.co.main.accountbook.model

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.Date
import java.util.Locale

internal enum class DateRangeOption(val label: String) {
    TODAY("당일"),
    ONE_WEEK("1주일"),
    ONE_MONTH("1개월"),
    THREE_MONTHS("3개월"),
    SIX_MONTHS("6개월"),
    OTHER("직접 선택");

    override fun toString(): String {
        return label
    }

    fun getDate(): Pair<LocalDate, LocalDate> {
        if (this == OTHER) {
            throw UnsupportedOperationException("cannot be called for OTHER")
        }
        val today = LocalDate.now()
        val startDate = when (this) {
            TODAY -> today
            ONE_WEEK -> today.minusWeeks(1)
            ONE_MONTH -> today.minusMonths(1)
            THREE_MONTHS -> today.minusMonths(3)
            SIX_MONTHS -> today.minusMonths(6)
            else -> throw IllegalStateException("Unexpected DateRangeOption")
        }
        return startDate to today
    }
}

internal fun formatNumber(number: Long): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(number)
}

internal fun getEndOfMonth(): String {
    val today = LocalDate.now()
    val year = today.year
    val month = today.monthValue
    val lastDay = YearMonth.of(year, month).lengthOfMonth()
    val endOfMonth = LocalDate.of(year, month, lastDay)

    return endOfMonth.toString()
}

internal fun String.contentDateFormat(): String {
    return try {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val targetFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        val date: Date? = originalFormat.parse(this)
        if (date != null) {
            targetFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}