package kr.co.main.accountbook.model

import java.text.NumberFormat
import java.time.LocalDate

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
    val formatter = NumberFormat.getNumberInstance()
    return formatter.format(number)
}
