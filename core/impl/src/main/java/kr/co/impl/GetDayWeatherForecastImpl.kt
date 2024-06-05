package kr.co.impl

import kr.co.domain.repository.WeatherForecastRepository
import kr.co.domain.usecase.GetDayWeatherForecast
import javax.inject.Inject

internal class GetDayWeatherForecastImpl @Inject constructor(
    private val repository: WeatherForecastRepository
): GetDayWeatherForecast {
    override suspend operator fun invoke() = repository.get()
}