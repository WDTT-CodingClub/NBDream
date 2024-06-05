package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.WeatherForecastResult
import kr.co.remote.model.response.GetWeatherForecastResponse

internal object WeatherRemoteMapper :
    Mapper<GetWeatherForecastResponse, WeatherForecastResult> {
    override fun convert(param: GetWeatherForecastResponse): WeatherForecastResult =
        with(param.resultData) {
            WeatherForecastResult(
                precipitation = precipitation,
                precipitationProbability = probability,
                humidity = humidity,
                windSpeed = windSpeed,
                temp = temp,
                items = items.map {
                    WeatherForecastResult.Item(
                        weather = it.weather,
                        minTemp = it.minTemp,
                        maxTemp = it.maxTemp,
                        day = it.day
                    )
                }
            )
        }
}