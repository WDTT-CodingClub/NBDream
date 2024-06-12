package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.WeatherForecastData
import kr.co.remote.model.response.GetWeatherForecastResponse

internal object WeatherRemoteMapper :
    Mapper<GetWeatherForecastResponse, WeatherForecastData> {
    override fun convert(param: GetWeatherForecastResponse): WeatherForecastData =
        with(param.resultData) {
            WeatherForecastData(
                precipitation = precipitation,
                precipitationProbability = probability,
                humidity = humidity,
                windSpeed = windSpeed,
                temp = temp,
                items = items.map {
                    WeatherForecastData.Item(
                        weather = it.weather,
                        minTemp = it.minTemp,
                        maxTemp = it.maxTemp,
                        day = it.day
                    )
                }
            )
        }
}