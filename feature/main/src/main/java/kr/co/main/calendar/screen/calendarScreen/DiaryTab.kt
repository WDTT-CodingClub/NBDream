package kr.co.main.calendar.screen.calendarScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kr.co.main.calendar.common.content.DiaryContent
import kr.co.main.calendar.screen.calendarScreen.calendar.DiaryCalendar
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.HolidayModel
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import java.time.LocalDate

@Composable
internal fun DiaryTab(
    calendarCrop: CropModel?,
    calendarYear: Int,
    calendarMonth: Int,
    selectedDate: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel>,
    diaries: List<DiaryModel>,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.gray9
    ) {
        Column {
            Card(
                modifier = Modifier.padding(Paddings.large),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                DiaryCalendar(
                    modifier = Modifier.padding(Paddings.xlarge),
                    cropColor = calendarCrop?.color?.color ?: Color.Transparent.toArgb(),
                    calendarYear = calendarYear,
                    calendarMonth = calendarMonth,
                    selectedDate = selectedDate,
                    onDateSelect = onDateSelect,
                    holidays = holidays,
                    diaries = diaries
                )
            }

            if (calendarCrop == null) {
                Text(
                    text = "재배 작물을 추가하고 영농 일지를 작성해 보세요."
                )
            }
            diaries.firstOrNull { it.date == selectedDate }?.let { diary ->
                Card(
                    modifier = Modifier.padding(Paddings.large),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    DiaryContent(
                        modifier = Modifier.padding(Paddings.xlarge),
                        diary = diary
                    )
                }
            }
        }
    }
}
