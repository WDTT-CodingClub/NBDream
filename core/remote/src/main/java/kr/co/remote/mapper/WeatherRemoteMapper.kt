package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.WeatherForecastData
import kr.co.remote.model.response.GetWeatherForecastResponse

internal object WeatherRemoteMapper :
    Mapper<GetWeatherForecastResponse, WeatherForecastData> {
    override fun convert(param: GetWeatherForecastResponse): WeatherForecastData =
        with(param) {
            WeatherForecastData(
                precipitation = shortTermWeather.precipitation,
                precipitationProbability = shortTermWeather.probability,
                humidity = shortTermWeather.humidity,
                windSpeed = shortTermWeather.windSpeed,
                day = shortTermWeather.day,
                minTemp = shortTermWeather.minTemp,
                maxTemp = shortTermWeather.maxTemp,
                temp = shortTermWeather.temp,
                items = longTermWeather.map {
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