package kr.co.data.source.remote

import kr.co.data.model.data.WeatherForecastData

interface WeatherRemoteDataSource {
    suspend fun get(): WeatherForecastData
}
