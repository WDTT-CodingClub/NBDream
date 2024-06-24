package kr.co.main.mapper.calendar

import kr.co.common.mapper.BaseMapper
import kr.co.domain.entity.WeatherForecastEntity
import kr.co.main.model.calendar.WeatherForecastModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal object WeatherForecastModelMapper
    : BaseMapper<WeatherForecastEntity, WeatherForecastModel>() {

    // TODO WeatherForecastEntity의 List<Weather> 프로퍼티에서 몇 번째 Weather 객체 정보 사용할지 생각하기
    // 일단 가장 첫 번째 객체 사용하도록 함, index 중간 값으로 하는게 나을지? 아니면 평균 값을 구할지?
    override fun toRight(left: WeatherForecastEntity): WeatherForecastModel =
        with(left) {
            WeatherForecastModel(
                date = LocalDate.parse(
                    weather.first().day,
                    DateTimeFormatter.ofPattern("yyyyMMdd")
                ),
                sky = when (weather.first().weather) {
                    "맑음" -> WeatherForecastModel.Sky.SUNNY
                    "구름 많음" -> WeatherForecastModel.Sky.PARTLY_CLOUDY
                    "흐림" -> WeatherForecastModel.Sky.MOSTLY_CLOUDY
                    else -> throw IllegalArgumentException("unknown sky")
                },
                maxTemp = weather.first().maxTemp.toInt().toString() + "°C",
                minTemp = weather.first().minTemp.toInt().toString() + "°C",
                precipitation = precipitation
            )
        }

    override fun toLeft(right: WeatherForecastModel): WeatherForecastEntity =
        with(right) {
            WeatherForecastEntity(
                precipitation = precipitation,
                probability = 0, //meaningless
                humidity = 0, //meaningless,
                temperature = 0.0f, //meaningless
                maxTemperature = maxTemp.substringBefore("°C").toFloat(),
                minTemperature = minTemp.substringBefore("°C").toFloat(),
                day = date.format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                windSpeed = 0, //meaningless
                weather = listOf(
                    WeatherForecastEntity.Weather(
                        weather = when (sky) {
                            WeatherForecastModel.Sky.SUNNY -> "맑음"
                            WeatherForecastModel.Sky.PARTLY_CLOUDY -> "구름 많음"
                            WeatherForecastModel.Sky.MOSTLY_CLOUDY -> "흐림"
                        },
                        minTemp = minTemp.substringBefore("°C").toFloat(),
                        maxTemp = maxTemp.substringBefore("°C").toFloat(),
                        day = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                    )
                )
            )
        }
}