package kr.co.wdtt.nbdream.domain.usecase

import kr.co.wdtt.nbdream.domain.repository.WeatherForecastRepository
import javax.inject.Inject

internal class GetDayWeatherForecast @Inject constructor(
    private val repository: WeatherForecastRepository
): IGetDayWeatherForecast {
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