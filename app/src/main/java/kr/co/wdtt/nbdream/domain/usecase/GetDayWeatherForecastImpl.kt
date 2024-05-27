package kr.co.wdtt.nbdream.domain.usecase

import kr.co.wdtt.nbdream.domain.repository.WeatherForecastRepository
import javax.inject.Inject

internal class GetDayWeatherForecastImpl @Inject constructor(
    private val repository: WeatherForecastRepository
): GetDayWeatherForecast {
    override suspend operator fun invoke(
        baseDate: String,
        baseTime: String,
        nx: String,
        ny: String
    ) = repository.getDayWeatherForecast(
        baseDate = baseDate,
        baseTime = baseTime,
        nx = nx,
        ny = ny
    )
}