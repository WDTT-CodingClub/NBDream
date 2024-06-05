package kr.co.wdtt.nbdream.ui.main.calendar.maincalendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import kr.co.wdtt.nbdream.domain.entity.HolidayEntity
import kr.co.wdtt.nbdream.ui.theme.colors
import kr.co.wdtt.nbdream.ui.theme.typo
import kr.co.wdtt.nbdream.ui.util.iterator
import java.time.LocalDate
import kotlin.math.max

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun MainCalendarHolidayRow(
    weekStartDate: LocalDate,
    weekEndDate: LocalDate,
    holidays: List<HolidayEntity>,
    modifier: Modifier = Modifier
) {
    val content = @Composable {
        for (date in weekStartDate..weekEndDate) {
            MainCalendarHolidayItem(
                holidays = holidays.filter { it.date == date }
            )
        }
    }

    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map { measurable ->
                measurable.measure(
                    constraints.copy(maxWidth = constraints.maxWidth / 7)
                )
            }

            var layoutHeight = 0
            placeables.forEach { layoutHeight = max(layoutHeight, it.height) }

            layout(
                width = constraints.maxWidth,
                height = layoutHeight
            ) {
                placeables.forEachIndexed { index, placeable ->
                    placeable.place(
                        x = getXPosition(
                            dayOfWeek = weekStartDate.plusDays(index.toLong()).dayOfWeek,
                            constraints = constraints
                        ),
                        y = 0
                    )
                }
            }
        }
    )
}

@Composable
private fun MainCalendarHolidayItem(
    holidays: List<HolidayEntity>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        for (holiday in holidays) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = holiday.name,
                style = MaterialTheme.typo.labelM,
                color = if (holiday.isHoliday) MaterialTheme.colors.red1 else MaterialTheme.colors.text2,
                textAlign = TextAlign.Center
            )
        }
    }
}