package kr.co.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetWeatherForecastResponse(
    @SerialName("shortTermWeatherRes")
    val shortTermWeather: ShortTermWeather,

    @SerialName("longTermWeatherRes")
    val longTermWeather: List<LongTermWeather>
) {
    @Serializable
    data class ShortTermWeather(
        @SerialName("date")
        val day: String,

        @SerialName("precipitationProbability")
        val probability: Int,

        @SerialName("precipitationAmount")
        val precipitation: Int,

        @SerialName("humidity")
        val humidity: Int,

        @SerialName("windSpeed")
        val windSpeed: Int,

        @SerialName("highestTemperature")
        val maxTemp: Float,

        @SerialName("lowestTemperature")
        val minTemp: Float,

        @SerialName("currentTemperature")
        val temp: Float,

        @SerialName("sky")
        val sky: String
    )

    @Serializable
    data class LongTermWeather(
        @SerialName("date")
        val day: String,

        @SerialName("sky")
        val weather: String,

        @SerialName("highestTemperature")
        val maxTemp: Float,

        @SerialName("lowestTemperature")
        val minTemp: Float
    )
}
