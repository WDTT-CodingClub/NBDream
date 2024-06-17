package kr.co.main.model.home

internal enum class WeatherMetrics(
    val label: String,
    val unit: String
) {
    Probability(
        label = "강수 확률",
        unit = "%"
    ),
    Precipitation(
        label = "강수량",
        unit = "mm"
    ),
    Humidity(
        label = "습도",
        unit = "%"
    ),
    Wind(
        label = "풍속",
        unit = "m/s"
    ),
}