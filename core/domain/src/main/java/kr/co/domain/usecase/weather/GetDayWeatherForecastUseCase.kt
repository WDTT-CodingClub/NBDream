package kr.co.domain.usecase.weather

import kr.co.domain.entity.WeatherForecastEntity
import kr.co.domain.repository.WeatherForecastRepository
import kr.co.domain.usecase.CachedSuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDayWeatherForecastUseCase @Inject constructor(
    private val repository: WeatherForecastRepository
): CachedSuspendUseCase<String, WeatherForecastEntity>(1, 3 * 60 * 60 * 1_000) {
    override suspend fun build(params: String?) = repository.get()
}