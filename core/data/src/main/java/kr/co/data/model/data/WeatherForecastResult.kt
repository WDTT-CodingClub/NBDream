package kr.co.data.model.data

data class WeatherForecastResult(
    val precipitation: Int,
    val precipitationProbability: Int,
    val humidity: String,
    val windSpeed: Int,
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