package kr.co.wdtt.nbdream.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.domain.entity.WeatherForecastEntity

interface WeatherForecastRepository {
    suspend fun getDayWeatherForecast(
        baseDate: String,
        nx: String,
        ny: String
    ): WeatherForecastEntity

    suspend fun getWeekWeatherForecast(
        baseDate: String,
        baseTime: String,
        nx: String,
        ny: String
    ): Flow<List<WeatherForecastEntity>>
}