package kr.co.domain.usecase.weather

import kr.co.domain.entity.WeatherForecastEntity
import kr.co.domain.repository.WeatherForecastRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDayWeatherForecastUseCase @Inject constructor(
    private val repository: WeatherForecastRepository
): SuspendUseCase<Unit, WeatherForecastEntity>() {
    override suspend fun build(params: Unit?) = repository.get()
}