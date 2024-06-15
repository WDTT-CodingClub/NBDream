package kr.co.domain.usecase

import kr.co.domain.entity.WeatherForecastEntity
import kr.co.domain.repository.WeatherForecastRepository
import javax.inject.Inject

class GetDayWeatherForecast @Inject constructor(
    private val repository: WeatherForecastRepository
): SuspendUseCase<Unit, WeatherForecastEntity>() {
    override suspend fun build(params: Unit?) = repository.get()
}