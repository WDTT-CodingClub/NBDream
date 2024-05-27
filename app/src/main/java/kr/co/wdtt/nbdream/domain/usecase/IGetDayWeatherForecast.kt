package kr.co.wdtt.nbdream.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.domain.entity.WeatherForecastEntity

interface IGetDayWeatherForecast {
    suspend operator fun invoke(
        baseDate: String,
        baseTime: String,
        nx: String,
        ny: String
    ): Flow<EntityWrapper<List<WeatherForecastEntity>>>
}