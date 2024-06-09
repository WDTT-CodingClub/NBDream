package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.WeatherForecastResult
import kr.co.domain.entity.WeatherForecastEntity
import javax.inject.Inject

internal object WeatherForecastMapper
    : Mapper<WeatherForecastResult, WeatherForecastEntity> {
    override fun convert(param: WeatherForecastResult): WeatherForecastEntity {
        return with(param) {
            WeatherForecastEntity(
                precipitation = precipitation.toString(),
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