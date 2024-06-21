package kr.co.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetWeatherForecastResponse(
    @SerialName("precipitationProbability")
    val probability: Int,

    @SerialName("precipitationAmount")
    val precipitation: Int,

    @SerialName("humidity")
    val humidity: Int,

    @SerialName("windSpeed")
    val windSpeed: Int,

    @SerialName("date")
    val day: String,

    @SerialName("currentTemperature")
    val temp: Float,

    @SerialName("lowestTemperature")
    val minTemp: Float,

    @SerialName("highestTemperature")
    val maxTemp: Float,

    @SerialName("longTermWeatherRes")
    val items: List<Item>,
) {
    @Serializable
    data class Item(
        @SerialName("sky")
        val weather: String,

        @SerialName("lowestTemperature")
        val minTemp: Float,

        @SerialName("highestTemperature")
        val maxTemp: Float,

        @SerialName("date")
        val day: String,
    )
}
