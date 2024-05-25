package kr.co.wdtt.nbdream.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.domain.entity.WeatherForecastEntity

interface WeatherForecastRepository {
    suspend fun getDayWeatherForecast(
        baseDate: String,
        baseTime: String,
        nx: String,
        ny: String
    ): Flow<EntityWrapper<List<WeatherForecastEntity>>>

    suspend fun getWeekWeatherForecast(
        baseDate: String,
        nx: String,
        ny: String
    ): Flow<EntityWrapper<List<WeatherForecastEntity>>>
}