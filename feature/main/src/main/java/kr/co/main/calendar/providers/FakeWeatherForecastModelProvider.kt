package kr.co.main.calendar.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.co.main.calendar.model.WeatherForecastModel
import java.time.LocalDate

class FakeWeatherForecastModelProvider : PreviewParameterProvider<WeatherForecastModel> {
    override val values = sequenceOf(
        WeatherForecastModel(
            date = LocalDate.of(2024, 5, 22),
            sky = WeatherForecastModel.Sky.SUNNY,
            minTemp = "25°C",
            maxTemp = "28°C",
            precipitation = 1
        )
    )
    override val count = values.count()
}