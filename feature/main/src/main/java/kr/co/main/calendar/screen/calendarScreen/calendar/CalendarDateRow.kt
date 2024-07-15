package kr.co.main.calendar.screen.calendarScreen.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kr.co.common.util.iterator
import kr.co.main.calendar.CalendarDesignToken
import kr.co.main.model.calendar.HolidayModel
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
internal fun CalendarDateRow(
    calendarMonth: Int,
    weekDateRange: ClosedRange<LocalDate>,
    selectedDate: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (date in weekDateRange) {
            CalendarDateItem(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onDateSelect(date) }
                    .alpha(
                        if (date.monthValue == calendarMonth) 1f
                        else 0.3f
                    ),
                date = date,
                isSelected = (date == selectedDate),
                isHoliday = holidays.any {
                    it.date == date && it.isHoliday
                } or (date.dayOfWeek == DayOfWeek.SUNDAY),
            )
        }
    }
}

@Composable
private fun CalendarDateItem(
    date: LocalDate,
    isSelected: Boolean,
    isHoliday: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
        if (isSelected) Color.Black
        else Color.Transparent

    val textColor =
        if (isHoliday) MaterialTheme.colors.red1
        else if (isSelected) Color.White
        else MaterialTheme.colors.text1

    Box(
        modifier = modifier
            .size(CalendarDesignToken.CALENDAR_ITEM_SIZE.dp)
            .clip(shape = CircleShape)
            .background(backgroundColor)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(Paddings.medium),
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typo.bodyR,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}