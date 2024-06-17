package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.WeatherForecastData
import kr.co.domain.entity.WeatherForecastEntity

internal object WeatherForecastMapper
    : Mapper<WeatherForecastData, WeatherForecastEntity> {
    override fun convert(param: WeatherForecastData): WeatherForecastEntity {
        return with(param) {
            WeatherForecastEntity(
                precipitation = precipitation,
                probability = precipitationProbability,
                temperature = temp,
                humidity = humidity,
                windSpeed = windSpeed,
                weather = items.map {
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