package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.WeatherForecastData
import kr.co.domain.entity.WeatherForecastEntity

internal object WeatherForecastMapper
    : Mapper<WeatherForecastData, WeatherForecastEntity> {
    override fun convert(param: WeatherForecastData): WeatherForecastEntity {
        return with(param) {
            WeatherForecastEntity(
                weather = weather,
                precipitation = precipitation,
                probability = precipitationProbability,
                temperature = temp,
                day = day,
                minTemperature = minTemp,
                maxTemperature = maxTemp,
                humidity = humidity,
                windSpeed = windSpeed,
                weathers = items.map {
                    WeatherForecastEntity.Weather(
                        weather = it.weather,
                        minTemp = it.minTemp,
                        maxTemp = it.maxTemp,
                        day = it.day
                    )
                }
            )
        }
    }

}