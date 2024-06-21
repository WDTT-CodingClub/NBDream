package kr.co.data.model.data

data class WeatherForecastData(
    val precipitation: Int,
    val precipitationProbability: Int,
    val humidity: Int,
    val windSpeed: Int,
    val day: String,
    val minTemp: Float,
    val maxTemp: Float,
    val temp: Float,
    val items: List<Item>
) {
    data class Item(
        val weather: String,
        val minTemp: Float,
        val maxTemp: Float,
        val day: String
    )
}