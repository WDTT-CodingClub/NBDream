package kr.co.domain.repository

import kr.co.domain.entity.WeatherForecastEntity

interface WeatherForecastRepository {
    suspend fun get(): WeatherForecastEntity
}