package kr.co.domain.usecase

import kr.co.domain.entity.WeatherForecastEntity

interface GetDayWeatherForecast {
    suspend operator fun invoke(): WeatherForecastEntity
}