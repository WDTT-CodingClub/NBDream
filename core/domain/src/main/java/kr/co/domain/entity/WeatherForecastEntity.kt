package kr.co.domain.entity

data class WeatherForecastEntity(
    val probability: Int,
    val precipitation: Int,
    val humidity: Int,
    val temperature: Float,
    val minTemperature: Float,
    val maxTemperature: Float,
    val day: String,
    val windSpeed: Int,
    val weather: List<Weather>,
) {
    data class Weather(
        val weather: String,
        val minTemp: Float,
        val maxTemp: Float,
        val day: String
    )

    enum class WeatherType {
        SUNNY, RAINY, CLOUDY,
    }
}
