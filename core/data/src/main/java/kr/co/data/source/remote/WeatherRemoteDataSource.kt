package kr.co.data.source.remote

import kotlinx.coroutines.flow.Flow
import kr.co.data.model.data.WeatherForecastResult

interface WeatherRemoteDataSource {
    suspend fun get(): WeatherForecastResult
}
