package kr.co.main.calendar.screen.addDiaryScreen.diary_input

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kr.co.main.calendar.common.CalendarDatePicker
import java.time.LocalDate

@Composable
internal fun DiaryDateInput(
    registerDate: LocalDate,
    weatherInfo: String,
    onRegisterDateInput: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CalendarDatePicker(
            date = registerDate,
            onDateInput = onRegisterDateInput
        )
    }
}

@Preview
@Composable
private fun DateInputPreview() {
    Surface {
        DiaryDateInput(
            registerDate = LocalDate.of(2024, 5, 22),
            weatherInfo = "25°C/13°C 1mm 미만 맑음",
            onRegisterDateInput = { _ -> }
        )
    }
}