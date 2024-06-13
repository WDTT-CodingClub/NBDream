package kr.co.main.calendar.calendar.maincalendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import kr.co.common.util.iterator
import kr.co.domain.entity.HolidayEntity
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.DayOfWeek
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun MainCalendarDateRow(
    weekStartDate: LocalDate,
    weekEndDate: LocalDate,
    holidays: List<HolidayEntity>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        for (date in weekStartDate..weekEndDate) {
            val isHoliday = (
                    holidays.any { (it.date == date) && (it.isHoliday) } or
                            (date.dayOfWeek == DayOfWeek.SUNDAY)
                    )

            Text(
                modifier = Modifier.weight(1f),
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typo.labelSB,
                color = if (isHoliday) MaterialTheme.colors.red1 else MaterialTheme.colors.text1,
                textAlign = TextAlign.Center
            )
        }
    }
}