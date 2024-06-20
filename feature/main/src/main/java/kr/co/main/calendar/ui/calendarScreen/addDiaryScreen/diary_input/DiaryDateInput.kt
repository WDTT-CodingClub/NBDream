package kr.co.main.calendar.ui.calendarScreen.addDiaryScreen.diary_input

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import kr.co.main.calendar.common.CalendarDatePicker
import kr.co.main.calendar.ui.common.CalendarWeather
import kr.co.main.calendar.model.WeatherForecastModel
import kr.co.main.calendar.providers.FakeWeatherForecastModelProvider
import java.time.LocalDate

@Composable
internal fun DiaryDateInput(
    registerDate: LocalDate,
    weatherForecast: WeatherForecastModel,
    onRegisterDateInput: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CalendarDatePicker(
            date = registerDate,
            onDateInput = onRegisterDateInput
        )
        CalendarWeather(weatherForecast = weatherForecast)
    }
}

@Preview
@Composable
private fun DateInputPreview(
    @PreviewParameter(FakeWeatherForecastModelProvider::class) weatherForecast: WeatherForecastModel
) {
    Surface {
        DiaryDateInput(
            registerDate = LocalDate.of(2024, 5, 22),
            weatherForecast = weatherForecast,
            onRegisterDateInput = { _ -> 2 }
        )
    }
}