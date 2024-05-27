package kr.co.wdtt.nbdream.domain.entity

data class WeatherForecastEntity(
    val forecastDate: String,
    val weather: String,
    val humidity: String,
    val temperature: String,
    val minTemperature: String,
    val maxTemperature: String,
    val precipitation: String,
    val windSpeed: String,
)
