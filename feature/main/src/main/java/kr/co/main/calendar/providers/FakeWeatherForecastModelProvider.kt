package kr.co.main.calendar.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.co.main.model.calendar.WeatherForecastModel
import java.time.LocalDate

internal class FakeWeatherForecastModelProvider : PreviewParameterProvider<WeatherForecastModel> {
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