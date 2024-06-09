package kr.co.data.repository

import kr.co.data.mapper.WeatherForecastMapper
import kr.co.data.source.remote.WeatherRemoteDataSource
import kr.co.domain.repository.WeatherForecastRepository
import javax.inject.Inject

internal class WeatherForecastRepositoryImpl @Inject constructor(
    private val remote: WeatherRemoteDataSource
) : WeatherForecastRepository {
    override suspend fun get() = remote.get().let(WeatherForecastMapper::convert)
}