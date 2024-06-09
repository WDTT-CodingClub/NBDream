package kr.co.domain.entity

data class WeatherForecastEntity(
    val probability: Int,
    val precipitation: String,
    val humidity: String,
    val temperature: Float,
    val windSpeed: Int,
    val weather: List<Weather>,
) {
    data class Weather(
        val weather: String,
        val minTemp: Float,
        val maxTemp: Float,
        val day: String
    )
}
