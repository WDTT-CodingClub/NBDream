package kr.co.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetWeatherForecastResponse(
    val resultCode: Int,
    val resultMessage: String,
    val resultData: ResultData
) {
    @Serializable
    data class ResultData(
        val probability: Int,
        val precipitation: Int,
        val humidity: String,
        val windSpeed: Int,
        val temp: Float,
        val items: List<Item>
    ) {
        @Serializable
        data class Item(
            val weather: String,
            val minTemp: Float,
            val maxTemp: Float,
            val day: String
        )
    }
}
